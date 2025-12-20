import utils
import cv2
import time
rectangleColor = (0,255,0)
rectangleColor_retrograde=(0, 0, 255)
rectangleColor_onlines = (0, 0, 255)
rectangleColor_redlight = (255, 0, 0)
rectangleColor_stop = (0, 0, 255)
rectangleColor_overspeed = (255, 0, 255)
def Retrograde_Dectate(scene, carLocation1, carLocation2, retrograde, offenseRecord, i, resultImage, image, saveXM9, conn, cur):
    [x2, y2, w2, h2] = carLocation2
    if utils.retrograde(scene, carLocation1[i], carLocation2[i]):
        if 2 in offenseRecord[i]:
            cv2.rectangle(resultImage, (x2, y2), (x2 + w2, y2 + h2), rectangleColor_retrograde, 2)
        else:
            if retrograde[i] >= 3:
                offenseRecord[i].append(2)
                save_image = image.copy()
                cv2.rectangle(save_image, (x2, y2), (x2 + w2, y2 + h2), rectangleColor_retrograde, 2)
                # time = frameCounter / fps
                print('CarID ' + str(i) + '车辆逆行')
                print("-----------------------------------Bounding Box坐标为:", [x2, y2, x2 + w2, y2 + h2])
                # 图片保存本地
                save_image = image.copy()
                cv2.rectangle(save_image, (x2, y2), (x2 + w2, y2 + h2), rectangleColor_retrograde, 10)
                file_path = saveXM9.save(save_image)
                file_name = (file_path.split('.')[0]).split('/')[1]
                pic_id = file_name.split('_')[0] + '_' + file_name.split('_')[1]
                data_time = time.strftime("%Y-%m-%d %H:%M:%S", time.localtime())
                file_path = file_path.split('/')[0] + '/' + file_path.split('/')[1]
                # sql操作
                sql = "insert into xm01_violation_total values(null,'"+pic_id+"',"+str(x2)+","+str(y2)+","+str(x2+w2)+","+str(y2+h2)+",'"+data_time+"','"+file_path+"',"+str(i)+");"
                print(sql)
                cur.execute(sql)
                conn.commit()
                cv2.rectangle(resultImage, (x2, y2), (x2 + w2, y2 + h2), rectangleColor_retrograde, 2)
            else:
                retrograde[i] += 1
    else:
        retrograde[i] = 0
        