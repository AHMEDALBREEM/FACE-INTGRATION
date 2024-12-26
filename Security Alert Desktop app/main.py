import firebase_admin
from firebase_admin import credentials, db
import tkinter as tk
from tkinter import messagebox
import pygame  # For playing sound
import threading  # For running the listener in a separate thread
import os
import sys

# Resolve the base directory where the resources are located
if getattr(sys, 'frozen', False):
    # If the application is frozen (running as a .exe)
    BASE_DIR = sys._MEIPASS
else:
    # If running in a normal Python environment
    BASE_DIR = os.path.dirname(os.path.abspath(__file__))

# Paths to resources
CREDENTIALS_PATH = os.path.join(BASE_DIR, "aimobdes-firebase-adminsdk-8ph5x-2a2f0af424.json")
MP3_PATH = os.path.join(BASE_DIR, "a.mp3")
ICON_PATH = os.path.join(BASE_DIR, "a.ico")

# Initialize Firebase credentials and database
cred = credentials.Certificate(CREDENTIALS_PATH)
firebase_admin.initialize_app(cred, {
    'databaseURL': "https://aimobdes-default-rtdb.europe-west1.firebasedatabase.app/"
})

# Initialize Pygame for sound playback
pygame.mixer.init()

# Global flag to prevent multiple alerts from opening at the same time
alert_active = False

def alert_window():
    """
    Creates a Tkinter window to notify the user of the alert.
    Provides an option to stop the alert and reset the database node.
    """
    global alert_active
    alert = tk.Toplevel()  # Create a top-level window (separate from main)
    alert.title("Face Recognition Alert")
    alert.geometry("300x200")
    alert.configure(bg='red')  # Set background color to red

    # Display the alert message
    label = tk.Label(alert, text="Unknown face detected!", bg='red', fg='white', font=('Helvetica', 16))
    label.pack(pady=20)

    # Create an 'End Alert' button to stop the alert
    end_button = tk.Button(alert, text="End Alert", command=lambda: end_alert(alert))
    end_button.pack(pady=10)

    alert.protocol("WM_DELETE_WINDOW", lambda: end_alert(alert))  # Handle window close button

def end_alert(alert):
    """
    Ends the alert by:
    - Resetting the 'unknown' node in Firebase to "false" (string).
    - Stopping the alert sound.
    - Closing the Tkinter alert window.
    """
    global alert_active
    ref = db.reference('unknown')
    ref.set("false")  # Set the value to "false" as a string
    pygame.mixer.music.stop()  # Stop the sound
    alert.destroy()  # Close the Tkinter window
    alert_active = False  # Reset the flag

def monitor_unknown_node():
    """
    Monitors the 'unknown' node in Firebase Realtime Database for changes.
    Triggers an alert window if the value updates to "true".
    """
    global alert_active

    def listener(event):
        """
        Callback function triggered on changes to the 'unknown' node.
        """
        global alert_active
        if event.data == "true" and not alert_active:
            print("Alert condition met! Triggering alert...")
            alert_active = True
            pygame.mixer.music.load(MP3_PATH)  # Load the alert sound
            pygame.mixer.music.play(-1)  # Play the sound in a loop
            alert_window()  # Open the alert window
        elif event.data == "false":
            print("Alert condition cleared.")

    ref = db.reference('unknown')
    ref.listen(listener)  # Attach the listener to the 'unknown' node

def create_main_window():
    """
    Creates the main application window for normal view.
    """
    root = tk.Tk()
    root.title("Face Recognition Monitoring")
    root.iconbitmap(ICON_PATH)  # Load the ICO icon

    # Set the window size
    window_width = 400
    window_height = 200

    # Get the screen width and height
    screen_width = root.winfo_screenwidth()
    screen_height = root.winfo_screenheight()

    # Calculate the x and y coordinates to center the window
    x = (screen_width // 2) - (window_width // 2)
    y = (screen_height // 2) - (window_height // 2)

    # Set the geometry of the window
    root.geometry(f"{window_width}x{window_height}+{x}+{y}")
    # Add an instruction label
    label = tk.Label(root, text="Monitoring Firebase for face recognition alerts...", font=('Helvetica', 14))
    label.pack(pady=20)
    root.geometry("400x200+100+100")  # Set width, height, and starting position (x=100, y=100)

    # Add a quit button
    quit_button = tk.Button(root, text="Exit", command=root.destroy)
    quit_button.pack(pady=20)

    return root

# Start the monitoring in a separate thread to keep the main thread responsive
monitor_thread = threading.Thread(target=monitor_unknown_node, daemon=True)
monitor_thread.start()

# Create and start the main application window
main_window = create_main_window()
main_window.mainloop()
