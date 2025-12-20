import utils
import cv2
import time
rectangleColor = (0,255,0)
rectangleColor_retrograde=(0, 0, 255)
rectangleColor_onlines = (0, 0, 255)
rectangleColor_redlight = (255, 0, 0)
rectangleColor_stop = (0, 0, 255)
rectangleColor_overspeed = (255, 0, 255)
def Parking_Dectate(scene, speed, carTmpLocation, light_image, stop, offenseRecord, i, resultImage, image, saveXM01, conn, cur):
    [x2, y2, w2, h2] = carTmpLocation
    if speed == 0:
        if scene.lane_type == 0 and not (
            utils.red_green_yellow(light_image, False) == 1 and utils.whichSection(scene,
            [x2, y2, w2,h2]) == 1 and utils.whichSide(
            scene.yellow1, x2 + w2, y2 + h2) == 1 and utils.whichSide(scene.yellow1, x2,
            y2 + w2) == 1) and not \
            scene.yellow2[0]['y'] > y2:
            stop[i] += 1
            if stop[i] >= 5 and 1 not in offenseRecord[i]:
                offenseRecord[i].append(1)
                # save_image = image.copy()
                # cv2.rectangle(save_image, (x2, y2), (x2 + w2, y2 + h2), rectangleColor_onlines, 10)
                print(str(i) + "号车违规停车")
                print("-----------------------------Bounding Box坐标为:", [x2, y2, x2 + w2, y2 + h2])
                # 图片保存本地
                save_image = image.copy()
                cv2.rectangle(save_image, (x2, y2), (x2 + w2, y2 + h2), rectangleColor_stop, 10)
                file_path = saveXM01.save(save_image)
                file_name = (file_path.split('.')[0]).split('\\')[1]
                pic_id = file_name.split('_')[0] + '_' + file_name.split('_')[1]
                data_time = time.strftime("%Y-%m-%d %H:%M:%S", time.localtime())
                file_path = file_path.split('\\')[0] + '/' + file_path.split('\\')[1]
                # sql操作
                sql = "insert into parking values(null,'"+pic_id+"',"+str(x2)+","+str(y2)+","+str(x2+w2)+","+str(y2+h2)+",'"+data_time+"','"+file_path+"',"+str(i)+");"
                print(sql)
                cur.execute(sql)
                conn.commit()
            if stop[i] >= 5:
                cv2.rectangle(resultImage, (x2, y2), (x2 + w2, y2 + h2), rectangleColor_stop, 10)
        else:
            stop[i] += 1
            if stop[i] >= 2 and 1 not in offenseRecord[i]:
                offenseRecord[i].append(1)
                # save_image = image.copy()
                # cv2.rectangle(save_image, (x2, y2), (x2 + w2, y2 + h2), rectangleColor_onlines, 10)
                # time = frameCounter / fps
                
                print(str(i) + "号车违规停车")
                print("-->Bounding Box坐标为:", [x2, y2, x2 + w2, y2 + h2])
                # 图片保存本地
                save_image = image.copy()
                cv2.rectangle(save_image, (x2, y2), (x2 + w2, y2 + h2), rectangleColor_stop, 10)
                file_path = saveXM01.save(save_image)
                file_name = (file_path.split('.')[0]).split('/')[1]
                pic_id = file_name.split('_')[0] + '_' + file_name.split('_')[1]
                data_time = time.strftime("%Y-%m-%d %H:%M:%S", time.localtime())
                file_path = file_path.split('/')[0] + '/' + file_path.split('/')[1]
                # sql操作
                cur.execute("DESCRIBE xm01_parking")
                columns = cur.fetchall()

                # 打印结果
                # print("表 xm01_statistic 的结构：")
                # for column in columns:
                #     print(column)
                # sql = "insert into xm01_parking values(null,'"+pic_id+"',"+str(x2)+","+str(y2)+","+str(x2+w2)+","+str(y2+h2)+",'"+data_time+"','"+file_path+"',"+str(i)+","+"0"+","+str(i)+");"
                # print(sql)
                # cur.execute(sql)
                # conn.commit()
            if stop[i] >= 2:
                cv2.rectangle(resultImage, (x2, y2), (x2 + w2, y2 + h2), rectangleColor_stop, 10)
    else:
        stop[i] = 0	
    return stop[i], offenseRecord[i], resultImage, cur, conn
