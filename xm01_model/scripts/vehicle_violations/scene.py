import json
import os


WIDTH = 720
HEIGHT = 560
scan_x = float(WIDTH) / float(1920)
scan_y = float(HEIGHT) / float(1080)

# class XM01_IO:


class scene:
    white1 = {0: {}, 1: {}}
    yellow1 = {0: {}, 1: {}}
    yellow2 = {0: {}, 1: {}}
    light = {0: {}, 1: {}}
    light2 = {0: {}, 1: {}}
    lane_type = 0
    def __init__(self, name, lane_type):
        print("Current working directory:", os.getcwd())
        jsonPath = "scenes/"
        jsonPath += name+".json"
        jsonfile = open(jsonPath, 'r')
        data = json.load(jsonfile)
        self.lane_type = lane_type
        for i in data:
            key, = i
            if key == "yellow_line1":
                self.yellow1[0]['x'] = list(i.values())[0][0]['x'] * scan_x
                self.yellow1[0]['y'] = list(i.values())[0][0]['y'] * scan_y
                self.yellow1[1]['x'] = list(i.values())[0][1]['x'] * scan_x
                self.yellow1[1]['y'] = list(i.values())[0][1]['y'] * scan_y
            if key == "white_line1":
                self.white1[0]['x'] = list(i.values())[0][0]['x'] * scan_x
                self.white1[0]['y'] = list(i.values())[0][0]['y'] * scan_y
                self.white1[1]['x'] = list(i.values())[0][1]['x'] * scan_x
                self.white1[1]['y'] = list(i.values())[0][1]['y'] * scan_y
            if key == "yellow_line2":
                self.yellow2[0]['x'] = list(i.values())[0][0]['x'] * scan_x
                self.yellow2[0]['y'] = list(i.values())[0][0]['y'] * scan_y
                self.yellow2[1]['x'] = list(i.values())[0][1]['x'] * scan_x
                self.yellow2[1]['y'] = list(i.values())[0][1]['y'] * scan_y
            if key == "light":
                self.light[0]['x'] = list(i.values())[0][0]['x'] * scan_x
                self.light[0]['y'] = list(i.values())[0][0]['y'] * scan_y
                self.light[1]['x'] = list(i.values())[0][1]['x'] * scan_x
                self.light[1]['y'] = list(i.values())[0][1]['y'] * scan_y
            if key == "light2":
                self.light2[0]['x'] = list(i.values())[0][0]['x'] * scan_x
                self.light2[0]['y'] = list(i.values())[0][0]['y'] * scan_y
                self.light2[1]['x'] = list(i.values())[0][1]['x'] * scan_x
                self.light2[1]['y'] = list(i.values())[0][1]['y'] * scan_y
