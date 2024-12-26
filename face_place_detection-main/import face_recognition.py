import face_recognition
import cv2
import numpy as np
import os
import firebase_admin
from firebase_admin import credentials, db

cred = credentials.Certificate("aimobdes-firebase-adminsdk-8ph5x-2a2f0af424.json")  # Update with your credentials path
firebase_admin.initialize_app(cred, {
    'databaseURL': "https://aimobdes-default-rtdb.europe-west1.firebasedatabase.app/"
})


def send_face_recognition_alert():
    ref = db.reference('unknown')
    ref.set("true") 



# Step 1: Recognize faces in the video and display their names
def recognize_faces_in_video(video_path, known_encodings, known_names, known_places, save_output=True):
    # Open the video file
    cap = cv2.VideoCapture(video_path)

    if not cap.isOpened():
        print(f"[ERROR] Unable to open video file: {video_path}")
        return

    # Define video properties
    input_fps = cap.get(cv2.CAP_PROP_FPS)
    input_width = int(cap.get(cv2.CAP_PROP_FRAME_WIDTH))
    input_height = int(cap.get(cv2.CAP_PROP_FRAME_HEIGHT))

    # Resize video for smaller frames
    output_size = (640, 360)  # Resize to a smaller resolution
    output_fps = input_fps if input_fps > 0 else 20.0  # Default FPS if input FPS is invalid
    output_path = "video_recognition_output.mp4"

    # VideoWriter setup for saving
    fourcc = cv2.VideoWriter_fourcc(*'mp4v')  # MP4 codec
    out = None
    if save_output:
        out = cv2.VideoWriter(output_path, fourcc, output_fps, output_size)

    print("[INFO] Starting face recognition on video... Press 'q' to exit manually.")

    while True:
        ret, frame = cap.read()
        if not ret:  # Exit when the video ends
            print("[INFO] End of video reached.")
            break

        # Resize frame for processing and output
        frame = cv2.resize(frame, output_size)

        # Convert frame to RGB (face_recognition uses RGB format)
        frame_rgb = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
        face_locations = face_recognition.face_locations(frame_rgb)
        face_encodings = face_recognition.face_encodings(frame_rgb, face_locations)

        # Annotate the frame
        for ((top, right, bottom, left), face_encoding) in zip(face_locations, face_encodings):
            matches = face_recognition.compare_faces(known_encodings, face_encoding, tolerance=0.6)
            name = "Unknown"
            place = "Unknown Location"

            face_distances = face_recognition.face_distance(known_encodings, face_encoding)
            if matches:
                best_match_index = np.argmin(face_distances)
                if matches[best_match_index]:
                    name = known_names[best_match_index]
                    place = known_places.get(name, "Unknown Location")

            # Draw rectangle and label
            cv2.rectangle(frame, (left, top), (right, bottom), (0, 255, 0), 2)
            label = f"{name} - {place}"
            if (name == "Unknown"):
                send_face_recognition_alert()
                # you can add the storge as your concern 

            
            cv2.putText(frame, label, (left, top - 10), cv2.FONT_HERSHEY_SIMPLEX, 0.6, (255, 255, 255), 2)

        # Show the frame with recognized faces
        cv2.imshow('Video Recognition', frame)

        # Save the frame to the output video
        if save_output and out:
            out.write(frame)

        # Break on 'q' key press
        if cv2.waitKey(1) & 0xFF == ord('q'):
            print("[INFO] Exiting on user request.")
            break

    # Release resources
    cap.release()
    if save_output and out:
        out.release()
    cv2.destroyAllWindows()

    if save_output:
        print(f"[INFO] Processed video saved to {output_path}")

# Step 2: Load known faces and places
def load_known_faces_and_places(known_faces_dir):
    known_encodings = []
    known_names = []
    known_places = {}

    name_to_place = {
        "Hasanul": "Prof.",
        "Muhammad": "Prof.",
        "Abu": "Prof.",
        "Kamrul": "Prof.",
        "Mohammad": "Vc",
        "lal person": "lal group"
    }

    for name_dir in os.listdir(known_faces_dir):
        name_path = os.path.join(known_faces_dir, name_dir)
        if not os.path.isdir(name_path):
            continue

        name_encodings = []
        for file_name in os.listdir(name_path):
            if file_name.lower().endswith(('.png', '.jpg', '.jpeg')):
                img_path = os.path.join(name_path, file_name)
                image = face_recognition.load_image_file(img_path)
                encodings = face_recognition.face_encodings(image)

                if encodings:
                    name_encodings.append(encodings[0])
                    print(f"[INFO] Loaded encoding for {name_dir} from {file_name}")
                else:
                    print(f"[WARNING] No faces found in {file_name}. Skipping.")

        if name_encodings:
            averaged_encoding = np.mean(name_encodings, axis=0)
            known_encodings.append(averaged_encoding)
            known_names.append(name_dir)
            known_places[name_dir] = name_to_place.get(name_dir, "Unknown Location")

    return known_encodings, known_names, known_places

def recognize_faces_in_photo(image_path, known_encodings, known_names, known_places, save_output=True):
    # Load the image file
    image = cv2.imread(image_path)
    if image is None:
        print(f"[ERROR] Unable to open image file: {image_path}")
        return

    # Convert the image to RGB (face_recognition uses RGB format)
    image_rgb = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
    face_locations = face_recognition.face_locations(image_rgb)
    face_encodings = face_recognition.face_encodings(image_rgb, face_locations)

    # Annotate the image
    for ((top, right, bottom, left), face_encoding) in zip(face_locations, face_encodings):
        matches = face_recognition.compare_faces(known_encodings, face_encoding, tolerance=0.6)
        name = "Unknown"
        place = "Unknown Location"

        face_distances = face_recognition.face_distance(known_encodings, face_encoding)
        if matches:
            best_match_index = np.argmin(face_distances)
            if matches[best_match_index]:
                name = known_names[best_match_index]
                place = known_places.get(name, "Unknown Location")

        # Draw rectangle and label
        cv2.rectangle(image, (left, top), (right, bottom), (0, 255, 0), 2)
        label = f"{name} - {place}"
        if (name == "Unknown"):
                send_face_recognition_alert()
                # you can add the storge as your concern 
        cv2.putText(image, label, (left, top - 10), cv2.FONT_HERSHEY_SIMPLEX, 3.9, (255, 255, 255), 2)

    cv2.namedWindow('Photo Recognition', cv2.WND_PROP_FULLSCREEN)
    cv2.setWindowProperty('Photo Recognition', cv2.WND_PROP_FULLSCREEN, cv2.WINDOW_FULLSCREEN)

    # Show the image with recognized faces
    cv2.imshow('Photo Recognition', image)

    # Save the annotated image if required
    if save_output:
        output_path = "photo_recognition_output.jpg"
        cv2.imwrite(output_path, image)
        print(f"[INFO] Processed image saved to {output_path}")

    # Wait for a key press and close the image window
    cv2.waitKey(0)
    cv2.destroyAllWindows()


# Step 3: Main program
if __name__ == "__main__":
    known_faces_directory = "known_faces"
    test_file_path = "test.jpg"  # Change this to your test file (image or video)

    if not os.path.isdir(known_faces_directory):
        print(f"[ERROR] The directory '{known_faces_directory}' does not exist.")
        exit(1)

    if not os.path.isfile(test_file_path):
        print(f"[ERROR] The file '{test_file_path}' does not exist.")
        exit(1)

    print("[INFO] Loading known faces and places...")
    known_encodings, known_names, known_places = load_known_faces_and_places(known_faces_directory)

    if not known_encodings:
        print("[ERROR] No known face encodings found. Exiting.")
        exit(1)

    # Determine if the test file is an image or a video based on the extension
    file_extension = os.path.splitext(test_file_path)[1].lower()

    if file_extension in ['.mp4', '.avi', '.mov', '.mkv']:  # Video file extensions
        print("[INFO] Starting video recognition...")
        recognize_faces_in_video(
            video_path=test_file_path,
            known_encodings=known_encodings,
            known_names=known_names,
            known_places=known_places,
            save_output=True
        )
    elif file_extension in ['.jpg', '.jpeg', '.png']:  # Image file extensions
        print("[INFO] Starting photo recognition...")
        recognize_faces_in_photo(
            image_path=test_file_path,
            known_encodings=known_encodings,
            known_names=known_names,
            known_places=known_places,
            save_output=True
        )
    else:
        print(f"[ERROR] Unsupported file type: {file_extension}. Please provide a valid image or video file.")
