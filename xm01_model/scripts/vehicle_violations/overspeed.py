import utils
import cv2
import time
rectangleColor = (0,255,0)
rectangleColor_retrograde=(0, 0, 255)
rectangleColor_onlines = (0, 0, 255)
rectangleColor_redlight = (255, 0, 0)
rectangleColor_stop = (0, 0, 255)
rectangleColor_overspeed = (255, 0, 255)
def OverSpeed_Dectate(scene, carLocation1, carLocation2, i, 
offenseRecord, resultImage, image, fps, conn, cur, saveXM9):
    [x1, y1, w1, h1] = carLocation1[i]
    [x2, y2, w2, h2] = carLocation2[i]
    carLocation1[i] = [x2, y2, w2, h2]
    speed = utils.estimateSpeed([x1, y1, w1, h1], [x2, y2, w2, h2], fps)
    speed *= 3.6
    if speed > 50:
        if 0 in offenseRecord[i]:
            cv2.rectangle(resultImage, (x2, y2), (x2 + w2, y2 + h2), rectangleColor_overspeed, 10)
        else:
            offenseRecord[i].append(0)
            save_image = image.copy()
            cv2.rectangle(save_image, (x2, y2), (x2 + w2, y2 + h2), rectangleColor_overspeed, 10)
            
            print('carid ' + str(i) + '车辆超速')
            print("-----------------------------Bounding Box坐标为:", [x2, y2, x2 + w2, y2 + h2])
            # 图片保存本地
            file_path = saveXM9.save(save_image)
            file_name = (file_path.split('.')[0]).split('/')[1]
            pic_id = file_name.split('_')[0] + '_' + file_name.split('_')[1]
            data_time = time.strftime("%Y-%m-%d %H:%M:%S", time.localtime())
            file_path = file_path.split('/')[0] + '/' + file_path.split('/')[1]
            # sql操作
            # sql = "insert into xm01_overspeeds values(null,'"+pic_id+"',"+str(x2)+","+str(y2)+","+str(x2+w2)+","+str(y2+h2)+",'"+data_time+"','"+file_path+"',"+str(i)+");"
            # print(sql)
            # cur.execute(sql)
            # conn.commit()
            # cv2.rectangle(resultImage, (x2, y2), (x2 + w2, y2 + h2), rectangleColor_overspeed, 10)
    return carLocation1[i], speed, resultImage, offenseRecord[i], cur, conn