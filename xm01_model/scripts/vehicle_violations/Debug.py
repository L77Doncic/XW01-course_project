import cv2
rectangleColor = (0,255,0)
rectangleColor_retrograde=(0, 0, 255)
rectangleColor_onlines = (0, 0, 255)
rectangleColor_redlight = (255, 0, 0)
rectangleColor_stop = (0, 0, 255)
rectangleColor_overspeed = (255, 0, 255)
def Debug_Short_Cut(scene, resultImage):
    cv2.line(resultImage, (int(scene.yellow1[1]['x']), int(scene.yellow1[1]['y'])), (int(scene.yellow1[0]['x']), int(scene.yellow1[0]['y'])), rectangleColor_onlines, 2)
    cv2.line(resultImage, (int(scene.white1[1]['x']), int(scene.white1[1]['y'])), (int(scene.white1[0]['x']), int(scene.white1[0]['y'])), rectangleColor_onlines, 2)
    cv2.line(resultImage, (int(scene.yellow2[1]['x']), int(scene.yellow2[1]['y'])), (int(scene.yellow2[0]['x']), int(scene.yellow2[0]['y'])), rectangleColor_onlines, 2)
    cv2.rectangle(resultImage, (int(scene.light[1]['x']), int(scene.light[1]['y'])), (int(scene.light[0]['x']), int(scene.light[0]['y'])), rectangleColor_onlines, 2)
    cv2.rectangle(resultImage, (int(scene.light2[1]['x']), int(scene.light2[1]['y'])), (int(scene.light2[0]['x']), int(scene.light2[0]['y'])), rectangleColor_onlines, 2)
    print(scene.light[0]['x'], scene.light[0]['y'], scene.light[1]['x'], scene.light[1]['y'], "!!!!!!!!!!!!!!!!!")