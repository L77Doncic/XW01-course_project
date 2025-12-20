import cv2
import dlib
import time
import threading
import math
import pymysql
import sys
import json
import utils
import scene
import os
import overspeed
# import parking
import press_yellow
import disobey_red_light
from Debug import Debug_Short_Cut
import save
import random

# video path
video_path_dir = "videos/"
video_ret_dir = "videos_ret/"
video_name_suffix = "test6_0.mp4"
video_name = "test6_0"

# traffic light per frame
light_img = -1

# save tmp location per car
carTmpLocation = None

# global video
video = -1

# connect to db
param = {
    'host': 'www.ylxteach.net',
    'port': 3366,
    'db': 'wtqjsz2025',
    'user': 'Administrator',
    'password': 'XWClassroom20202023',
    'charset': 'utf8',
    'autocommit': True
}
conn = pymysql.connect(**param)
cur = conn.cursor()

# clear statistics for data display
'''
每次统计最新的车流量，删除之前视频的车流量
'''
sql = 'delete from xm01_statistic'
cur.execute(sql)
conn.commit()

# read classifier
carCascade = cv2.CascadeClassifier('./car_recognition_threshold.xml')

# type of violation
offense = {0: "OverSpeeding", 1: "Violate_Parking", 2: "Retrograde", 3: "PressYellowLine", 4: "RedLight"}

# map length, width to processing space
WIDTH = 1280
HEIGHT = 720
carWidth = 1.8


def track(video, scene, saveXM01):
    global video_name_suffix
    global conn
    global cur
    fourcc = cv2.VideoWriter_fourcc(*'mp4v')
    out = cv2.VideoWriter(
        "./4result.mp4",
        fourcc, video.get(cv2.CAP_PROP_FPS),
        (1280, 720))
    rectangleColor = (0, 255, 0)
    rectangleColor_retrograde = (0, 0, 255)
    rectangleColor_onlines = (0, 0, 255)
    rectangleColor_redlight = (255, 0, 0)
    rectangleColor_stop = (0, 0, 255)
    rectangleColor_overspeed = (255, 0, 255)
    frameCounter = 0
    currentCarID = 0
    fps = 0
    fps = video.get(cv2.CAP_PROP_FPS)
    carcounter = 0  # assist in statistics of traffic flow
    speed = 0
    trafficFlow = 0
    carTracker = {}
    offenseRecord = {}
    stop = {}
    retrograde = {}
    carLocation1 = {}
    carLocation2 = {}
    # carLeaveCount=0
    carcounter = 0
    while True:
        rc, image = video.read()
        if type(image) == type(None):
            break
        image = cv2.resize(image, (WIDTH, HEIGHT))
        resultImage = image.copy()
        # cv2.line(resultImage, ptStart, ptEnd, point_color, thickness, lineType)
        frameCounter = frameCounter + 1
        if frameCounter % 3 == 0 or frameCounter == 1:
            resultImage = image.copy()

        carIDtoDelete = []
        for carID in carTracker.keys():
            trackingQuality = carTracker[carID].update(image)
            if trackingQuality < 7 or carTracker[carID].get_position().width() > 100 or carTracker[
                carID].get_position().height() > 100:
                carIDtoDelete.append(carID)
                carcounter += 1

        for carID in carIDtoDelete:
            print('Removing carID ' + str(carID) + ' from list of trackers.')
            print('Removing carID ' + str(carID) + ' previous location.')
            print('Removing carID ' + str(carID) + ' current location.')
            carTracker.pop(carID, None)
            offenseRecord.pop(carID)
            stop.pop(carID)
            carLocation1.pop(carID, None)
            carLocation2.pop(carID, None)

        if not (frameCounter % 3):
            gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
            cars = carCascade.detectMultiScale(gray, 1.1, 13, 0, (24, 24))

            for (_x, _y, _w, _h) in cars:
                x = int(_x)
                y = int(_y)
                w = int(_w)
                h = int(_h)

                x_bar = x + 0.5 * w
                y_bar = y + 0.5 * h

                matchCarID = None

                for carID in carTracker.keys():
                    trackedPosition = carTracker[carID].get_position()

                    t_x = int(trackedPosition.left())
                    t_y = int(trackedPosition.top())
                    t_w = int(trackedPosition.width())
                    t_h = int(trackedPosition.height())

                    t_x_bar = t_x + 0.5 * t_w
                    t_y_bar = t_y + 0.5 * t_h

                    if ((t_x <= x_bar <= (t_x + t_w)) and (t_y <= y_bar <= (t_y + t_h)) and (
                            x <= t_x_bar <= (x + w)) and (y <= t_y_bar <= (y + h))):
                        matchCarID = carID

                if matchCarID is None:
                    print('Creating new tracker ' + str(currentCarID))
                    offenseRecord[currentCarID] = []
                    stop[currentCarID] = 0
                    retrograde[currentCarID] = 0
                    tracker = dlib.correlation_tracker()
                    tracker.start_track(image, dlib.rectangle(x, y, x + w, y + h))

                    carTracker[currentCarID] = tracker
                    carLocation1[currentCarID] = [x, y, w, h]
                    currentCarID = currentCarID + 1

        for carID in carTracker.keys():
            trackedPosition = carTracker[carID].get_position()

            t_x = int(trackedPosition.left())
            t_y = int(trackedPosition.top())
            t_w = int(trackedPosition.width())
            t_h = int(trackedPosition.height())

            cv2.rectangle(resultImage, (t_x, t_y), (t_x + t_w, t_y + t_h), rectangleColor, 5)
            cv2.putText(resultImage, 'carid: %s' % carID, (t_x, t_y), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (70, 70, 200), 2)

            carLocation2[carID] = [t_x, t_y, t_w, t_h]

        # get fps
        fps = video.get(cv2.CAP_PROP_FPS)

        # traffic flow
        trafficFlow = int((currentCarID - carcounter) / (3 / fps))

        cv2.putText(resultImage, 'traffic flow:' + str(trafficFlow) + '/min',
                    (450, 30), cv2.FONT_HERSHEY_SIMPLEX, 0.75, (255, 0, 255), 2)

        for i in carLocation1.keys():
            trackedPosition = carTracker[i].get_position()
            t_x = int(trackedPosition.left())
            t_y = int(trackedPosition.top())

            if frameCounter % 3 == 0:

                # sql insert traffic flow chart
                sql = "INSERT INTO xm01_statistic (traffic_flow, wid_x1, wid_y1, wid_x2, wid_y2, create_time) VALUES (%s, %s, %s, %s, %s, NOW())"
                print(sql)
                cur.execute("DESCRIBE xm01_statistic")
                columns = cur.fetchall()

                # 打印结果
                print("表 xm01_statistic 的结构：")
                for column in columns:
                    print(column)
                cur.execute(sql, (trafficFlow, random.uniform(103, 104), random.uniform(30, 31), random.uniform(103, 104), random.uniform(30, 31)))
                conn.commit()

                speed = 0
                [x1, y1, w1, h1] = carLocation1[i]
                [x2, y2, w2, h2] = carLocation2[i]
                carTmpLocation = [x2, y2, w2, h2]

                # overspeed_dectate
                light_image = image[int(scene.light[1]['y']):int(scene.light[0]['y']),
                              int(scene.light[0]['x']):int(scene.light[1]['x'])]
                overspeed_ret = overspeed.OverSpeed_Dectate(scene, carLocation1, carLocation2, i,
                                                            offenseRecord, resultImage, image, fps, conn, cur, saveXM01)

                carLocation1[i], speed, resultImage, offenseRecord[i], cur, conn = overspeed_ret

                # parking dectate
                light2_image = -1
                if scene.lane_type == 1:
                    light2_image = image[int(scene.light2[1]['y']):int(scene.light2[0]['y']),
                                   int(scene.light2[0]['x']):int(scene.light2[1]['x'])]

                # parking_ret = parking.Parking_Dectate(scene, speed, [x2, y2, w2, h2],
                #                                       light_image, stop, offenseRecord, i, resultImage, image, saveXM01,
                #                                       conn, cur)
                # stop[i], offenseRecord[i], resultImage, cur, conn = parking_ret

                # show speed per car
                cv2.putText(resultImage, 'speed:%s' % str("%.2f" % round(speed, 2)) + 'Km/h', (t_x, t_y + 15),
                            cv2.FONT_HERSHEY_SIMPLEX, 0.5, (70, 70, 200), 2)

                # press yellow dectate
                yellow_ret = press_yellow.Press_Yellow_Dectate(scene, [x2, y2, w2, h2], offenseRecord, i,
                                                               resultImage, image, saveXM01, conn, cur)
                offenseRecord[i], resultImage, cur, conn = yellow_ret

                # disobey red light dectate
                red_ret = disobey_red_light.Disobey_Red_Light(scene, [x2, y2, w2, h2], offenseRecord, i,
                                                              light_image, resultImage, image, saveXM01, conn, cur)
                offenseRecord[i], resultImage, cur, conn = red_ret

        if frameCounter % 3 == 0:
            # debug type 1 can open
            # Debug_Short_Cut(scene, resultImage)
            cv2.imshow('result', resultImage)
            # cv2.waitKey(5)
            out.write(resultImage)
            out.write(resultImage)
            out.write(resultImage)
        if cv2.waitKey(33) == 27:
            break

    cv2.destroyAllWindows()
    cur.close()
    conn.close()
    out.release()
    video.release()


if __name__ == '__main__':
    scan_x = float(WIDTH) / float(1536)
    scan_y = float(HEIGHT) / float(480)
    # call `python -u path video_name`
    video_name_suffix = "test4_0.mp4"
    # video_name_suffix = sys.argv[1]
    video_path = os.path.join(video_path_dir, video_name_suffix)
    video_name = video_name_suffix.split('.')[0]
    print(video_name)
    temp_scene = scene.scene(video_name, int(video_name.split('_')[1]))
    saveXM01 = save.saveRecord(video_name)
    video = cv2.VideoCapture(video_path)
    rc, image2mark = video.read()
    image2mark = cv2.resize(image2mark, (WIDTH, HEIGHT))
    track(video, temp_scene, saveXM01)
