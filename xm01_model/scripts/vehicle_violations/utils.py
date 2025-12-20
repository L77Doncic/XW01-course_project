import math
import cv2
import numpy as np
import matplotlib.pyplot as plt
carWidth = 1.8

# 测速
def estimateSpeed(location1, location2, fps):
    d_pixels = math.sqrt(math.pow(location2[0] - location1[0], 2) + math.pow(location2[1] - location1[1], 2))
    ppm = location2[2] / carWidth
    d_meters = d_pixels / ppm
    speed = d_meters * fps / 3
    bias = 2.5
    return speed * bias

def whichSection(scene, car):
    if car[1] + car[3] >= scene.yellow1[1]['y']:
        return 1
    elif scene.yellow2[0]['y'] < car[1] + car[3] < scene.yellow1[1]['y']:
        return 0
    else:
        return -1
def whichSection2(scene, car):
    if car[1] + car[3] >= scene.yellow2[1]['y']:
        return 1
    else:
        return 0
    
# 红绿灯判断辅助
def findNoneZero(rgb_image):
    rows, cols, _ = rgb_image.shape
    counter = 0
    for row in range(rows):
        for col in range(cols):
            pixels = rgb_image[row, col]
            if sum(pixels) != 0:
                counter = counter + 1
    return counter


# 红绿灯判断
def red_green_yellow(rgb_image, display):
    hsv = cv2.cvtColor(rgb_image, cv2.COLOR_RGB2HSV)
    avg_saturation = np.average(hsv[:, :, 1])  # 统计亮度

    sat_low = int(avg_saturation * 1.3)
    val_low = 140
    # Green
    lower_green = np.array([0, 0, 0])
    upper_green = np.array([85, 255, 255])
    green_mask = cv2.inRange(hsv, lower_green, upper_green)
    green_result = cv2.bitwise_and(rgb_image, rgb_image, mask=green_mask)

    # Red
    # lower_red = np.array([85, sat_low, val_low])
    lower_red = np.array([85, 0, 0])
    upper_red = np.array([170, 255, 255])
    red_mask = cv2.inRange(hsv, lower_red, upper_red)
    red_result = cv2.bitwise_and(rgb_image, rgb_image, mask=red_mask)
    if display == True:
        _, ax = plt.subplots(1, 4, figsize=(20, 10))
        ax[0].set_title('rgb image')
        ax[0].imshow(rgb_image)
        ax[1].set_title('red result')
        ax[1].imshow(red_result)
        ax[2].set_title('green result')
        ax[2].imshow(green_result)
        ax[3].set_title('hsv image')
        ax[3].imshow(hsv)
        plt.show()
    sum_green = findNoneZero(green_result)
    sum_red = findNoneZero(red_result)
    print(r"sum_green=%d, sum_red=%d"%(sum_green, sum_red))
    if sum_red >= sum_green:
        return 1  # Red
    return 0  # green

# 判断点（x,y)在直线的哪一边
def whichSide(line, x, y):
    x1 = line[0]['x']
    x2 = line[1]['x']
    y1 = line[0]['y']
    y2 = line[1]['y']
    a = 1.0 * (y2 - y1) / (x2 - x1)
    b = -1 * x1 * a + y1
    if (y - a * x - b) <= 0:
        return -1
    else:
        return 1

def redLight(scene, car, light_image):
    isRedLight = False
    res = whichSection(scene, car)
    if res == 1:
        if red_green_yellow(light_image, False) == 1:
            if whichSide(scene.yellow1, car[0] + car[2], car[1] + car[3]) == 1 and whichSide(scene.yellow1,
                                                                                                car[0],
                                                                                                car[1] + car[3]) == 1:
                if whichSide(scene.white1, car[0] + car[2], car[1] + 0.75 * car[3]) == -1:
                    isRedLight = True
    return isRedLight

def onLines(scene, car):
    isOnLines = False
    res = whichSection(scene, car)
    if res == 1:
        if whichSide(scene.yellow1, car[0] + car[2], car[1] + car[3]) == 1:  # right->left
            if whichSide(scene.yellow1, car[0], car[1] + car[3]) == -1:
                isOnLines = True
        elif whichSide(scene.yellow1, car[0], car[1] + car[3]) == -1:  # left->right
            if whichSide(scene.yellow1, car[0] + car[2], car[1] + car[3]) == 1:
                isOnLines = True
        else:
            isOnLines = True
    return isOnLines

def retrograde(scene, pre_loc, now_loc):
    isRetrograde = False
    res = whichSection(scene, now_loc)
    if abs(now_loc[1] - pre_loc[1]) - abs(now_loc[0] - pre_loc[0]) >= 2:
        if res == 1:
            if whichSide(scene.yellow1, now_loc[0] + now_loc[2],
                         now_loc[1] + now_loc[3]) == 1:  # right
                if now_loc[1] - pre_loc[1] > 0:
                    isRetrograde = True
            elif whichSide(scene.yellow1, now_loc[0], now_loc[1] + now_loc[3]) == -1:  # left
                if pre_loc[1] - now_loc[1] > 0:
                    isRetrograde = True
        elif res == 0:
            if whichSide(scene.virtual, now_loc[0] + now_loc[2],
                         now_loc[1] + now_loc[3]) == 1:  # right
                if now_loc[1] - pre_loc[1] > 0:
                    isRetrograde = True
            elif whichSide(scene.virtual, now_loc[0], now_loc[1] + now_loc[3]) == -1:  # left
                if pre_loc[1] - now_loc[1] > 0:
                    isRetrograde = True
        else:
            if whichSide(scene.yellow2, now_loc[0] + now_loc[2],
                         now_loc[1] + now_loc[3]) == 1:  # right
                if now_loc[1] - pre_loc[1] >= 2:
                    isRetrograde = True
            elif whichSide(scene.yellow2, now_loc[0], now_loc[1] + now_loc[3]) == -1:  # left
                if pre_loc[1] - now_loc[1] >= 2:
                    isRetrograde = True
        return isRetrograde