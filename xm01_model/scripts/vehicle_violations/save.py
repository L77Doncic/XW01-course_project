import cv2
import os
class saveRecord:
    record_save_dir = "images"
    counter = 0
    video_name = ""
    def __init__(self, video_name):
        self.video_name = video_name
        self.counter = 0
    def save(self, image):
        save_image = image.copy()
        file_name = self.video_name.split('.')[0] + '_' + str(self.counter) + '.png'
        self.counter += 1
        file_path = os.path.join(self.record_save_dir, file_name)
        cv2.imwrite(file_path, save_image)
        print("file %s saved successfully!"%(file_path))
        return file_path

    