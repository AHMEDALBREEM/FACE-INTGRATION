package ps.albreem.software;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.Intent;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.media.MediaPlayer;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;

public class MainActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private HashMap<String, Object> map = new HashMap<>();
	
	private LinearLayout linear1;
	private TextView textview1;
	private Button button1;
	
	private MediaPlayer as;
	private DatabaseReference a = _firebase.getReference("unknown");
	private ChildEventListener _a_child_listener;
	private Intent intent = new Intent();
	private TimerTask timer;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		textview1 = findViewById(R.id.textview1);
		button1 = findViewById(R.id.button1);
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (button1.getText().toString().equals("stop")) {
					
					
					DatabaseReference dbRef1 = FirebaseDatabase.getInstance().getReference("unknown");
					dbRef1.setValue("false");
					if (as.isPlaying()) {
						as.pause();
						as.release();
					}
					intent.setClass(getApplicationContext(), MainActivity.class);
					startActivity(intent);
					finish();
				}
				else {
					finishAffinity();
				}
			}
		});
		
		_a_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("unknown");
				
				dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
					    @Override
					    public void onDataChange(@NonNull DataSnapshot snapshot) {
						        if (snapshot.exists()) {
							            String value = snapshot.getValue(String.class);
							            
							  if (value != null && value.toLowerCase().equals("true"))  {
								                // Use the retrieved value
								                button1.setText("stop");
								
								
								button1.setText("stop");
																linear1.setBackgroundColor(0xFFF44336);
																	textview1.setTextColor(0xFFFFFFFF);
																	textview1.setText("Unknown Person Recognized !!");
																
																
								
								       as = MediaPlayer.create(getApplicationContext(), R.raw.asd);
								as.start();
								as.setLooping(true);
								
								            } else {
								                Log.d("FirebaseValue", "'unknown' key exists but has no value.");
								            }
							        } else {
							            Log.d("FirebaseValue", "'unknown' key does not exist in the database.");
							        }
						    }
					
					    @Override
					    public void onCancelled(@NonNull DatabaseError error) {
						        Log.e("FirebaseError", "Error retrieving data: " + error.getMessage());
						    }
				});
				
				
				
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("unknown");
				
				dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
					    @Override
					    public void onDataChange(@NonNull DataSnapshot snapshot) {
						        if (snapshot.exists()) {
							            String value = snapshot.getValue(String.class);
							            
							  if (value != null && value.toLowerCase().equals("true"))  {
								                // Use the retrieved value
								                button1.setText("stop");
								button1.setText("stop");
																linear1.setBackgroundColor(0xFFF44336);
																	textview1.setTextColor(0xFFFFFFFF);
																	textview1.setText("Unknown Person Recognized !!");
																
																
								       as = MediaPlayer.create(getApplicationContext(), R.raw.asd);
								as.start();
								as.setLooping(true);
								
								            } else {
								                Log.d("FirebaseValue", "'unknown' key exists but has no value.");
								            }
							        } else {
							            Log.d("FirebaseValue", "'unknown' key does not exist in the database.");
							        }
						    }
					
					    @Override
					    public void onCancelled(@NonNull DatabaseError error) {
						        Log.e("FirebaseError", "Error retrieving data: " + error.getMessage());
						    }
				});
				
				
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("unknown");
				
				dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
					    @Override
					    public void onDataChange(@NonNull DataSnapshot snapshot) {
						        if (snapshot.exists()) {
							            String value = snapshot.getValue(String.class);
							            
							  if (value != null && value.toLowerCase().equals("true"))  {
								                // Use the retrieved value
								                button1.setText("stop");
								button1.setText("stop");
																linear1.setBackgroundColor(0xFFF44336);
																	textview1.setTextColor(0xFFFFFFFF);
																	textview1.setText("Unknown Person Recognized !!");
																
																
								       as = MediaPlayer.create(getApplicationContext(), R.raw.asd);
								as.start();
								as.setLooping(true);
								
								            } else {
								                Log.d("FirebaseValue", "'unknown' key exists but has no value.");
								            }
							        } else {
							            Log.d("FirebaseValue", "'unknown' key does not exist in the database.");
							        }
						    }
					
					    @Override
					    public void onCancelled(@NonNull DatabaseError error) {
						        Log.e("FirebaseError", "Error retrieving data: " + error.getMessage());
						    }
				});
				
				
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		a.addChildEventListener(_a_child_listener);
	}
	
	private void initializeLogic() {
		new Thread(new Runnable() {
				    @Override
				    public void run() {
						        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("unknown");
						
						        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
								            @Override
								            public void onDataChange(@NonNull DataSnapshot snapshot) {
										                if (snapshot.exists()) {
												                    String value = snapshot.getValue(String.class);
												
												                    if (value != null && value.equalsIgnoreCase("true")) {
														               
														
														button1.setText("stop");
														linear1.setBackgroundColor(0xFFF44336);
															textview1.setTextColor(0xFFFFFFFF);
															textview1.setText("Unknown Person Recognized !!");
														
														
														
														         MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.asd);
														                        mediaPlayer.setLooping(true);
														                        mediaPlayer.start();
														                    } else {
														                        Log.d("FirebaseValue", "'unknown' key exists but has no value.");
														                    }
												                } else {
												                    Log.d("FirebaseValue", "'unknown' key does not exist in the database.");
												                }
										            }
								
								            @Override
								            public void onCancelled(@NonNull DatabaseError error) {
										                Log.e("FirebaseError", "Error retrieving data: " + error.getMessage());
										            }
								        });
						    }
		}).start();
		DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("unknown");
		
		dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
			    @Override
			    public void onDataChange(@NonNull DataSnapshot snapshot) {
				        if (snapshot.exists()) {
					            String value = snapshot.getValue(String.class);
					            
					  if (value != null && value.toLowerCase().equals("true"))  {
						                // Use the retrieved value
						                button1.setText("stop");
						button1.setText("stop");
														linear1.setBackgroundColor(0xFFF44336);
															textview1.setTextColor(0xFFFFFFFF);
															textview1.setText("Unknown Person Recognized !!");
														
														
						       as = MediaPlayer.create(getApplicationContext(), R.raw.asd);
						as.start();
						as.setLooping(true);
						
						            } else {
						                Log.d("FirebaseValue", "'unknown' key exists but has no value.");
						            }
					        } else {
					            Log.d("FirebaseValue", "'unknown' key does not exist in the database.");
					        }
				    }
			
			    @Override
			    public void onCancelled(@NonNull DatabaseError error) {
				        Log.e("FirebaseError", "Error retrieving data: " + error.getMessage());
				    }
		});
		
		
		
		timer = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("unknown");
						
						dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
							    @Override
							    public void onDataChange(@NonNull DataSnapshot snapshot) {
								        if (snapshot.exists()) {
									            String value = snapshot.getValue(String.class);
									            
									  if (value != null && value.toLowerCase().equals("true"))  {
										                // Use the retrieved value
										                button1.setText("stop");
										button1.setText("stop");
																		linear1.setBackgroundColor(0xFFF44336);
																			textview1.setTextColor(0xFFFFFFFF);
																			textview1.setText("Unknown Person Recognized !!");
																		
																		
										       as = MediaPlayer.create(getApplicationContext(), R.raw.asd);
										as.start();
										as.setLooping(true);
										
										            } else {
										                Log.d("FirebaseValue", "'unknown' key exists but has no value.");
										            }
									        } else {
									            Log.d("FirebaseValue", "'unknown' key does not exist in the database.");
									        }
								    }
							
							    @Override
							    public void onCancelled(@NonNull DatabaseError error) {
								        Log.e("FirebaseError", "Error retrieving data: " + error.getMessage());
								    }
						});
						
						
						
					}
				});
			}
		};
		_timer.scheduleAtFixedRate(timer, (int)(5000), (int)(60000));
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}