package com.accelerogps;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
 
/**
 * Android accelerometer sensor tutorial
 * @author antoine vianey
 * under GPL v3 : http://www.gnu.org/licenses/gpl-3.0.html
 */
public class Accelerometer extends Activity 
        implements AccelerometerListener {
	
	public static float velocityX=0;
	public static float velocityY=0;
	public static float velocityZ=0;
 
    private static Context CONTEXT;
 
    private Timer t;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        CONTEXT = this;
        
        this.t = new Timer();
        this.t.scheduleAtFixedRate(new UpdateValue(this), 1000, 500);
    }
 
    protected void onResume() {
        super.onResume();
        if (AccelerometerManager.isSupported()) {
            AccelerometerManager.startListening(this);
        }
    }
    
    public static void updateSections() {
    	
    	
    }
 
    protected void onDestroy() {
        super.onDestroy();
        if (AccelerometerManager.isListening()) {
            AccelerometerManager.stopListening();
        }
 
    }
 
    public static Context getContext() {
        return CONTEXT;
    }
    
    class UpdateValue extends TimerTask {
    	
    	Accelerometer pointer;
    	UpdateValue(Accelerometer pointer) {
    		this.pointer = pointer; 
    	}
        public void run() {
        	this.pointer.runOnUiThread(new UpdateFieldsTask());     	
        }
      }
    
    class UpdateFieldsTask implements Runnable {

		@Override
		public void run() {
			
			((TextView) findViewById(R.id.current_velocity_x)).setText(String.valueOf(Accelerometer.velocityX));
	        ((TextView) findViewById(R.id.current_velocity_y)).setText(String.valueOf(Accelerometer.velocityY));
	        ((TextView) findViewById(R.id.current_velocity_z)).setText(String.valueOf(Accelerometer.velocityZ));
	        
	        ((TextView) findViewById(R.id.distx_t)).setText(String.valueOf(Accelerometer.x));
	        ((TextView) findViewById(R.id.disty_t)).setText(String.valueOf(Accelerometer.y));
	        ((TextView) findViewById(R.id.distz_t)).setText(String.valueOf(Accelerometer.z));
			
		}
    	
    }
 

    public void onShake(float force) {
        Toast.makeText(this, "Phone shaked : " + force, 1000).show();
    }
 
    public static float timeDiff;
    
    public static float x;
    public static float y;
    public static float z;

    public void onAccelerationChanged(float x, float y, float z) {

        ((TextView) findViewById(R.id.x)).setText(String.valueOf(x));
        ((TextView) findViewById(R.id.y)).setText(String.valueOf(y));
        ((TextView) findViewById(R.id.z)).setText(String.valueOf(z));        
        
        float movementX = (float) (0.5 * x * Accelerometer.timeDiff*Accelerometer.timeDiff);
        float movementY = (float) (0.5 * y * Accelerometer.timeDiff*Accelerometer.timeDiff);
        float movementZ = (float) (0.5 * z * Accelerometer.timeDiff*Accelerometer.timeDiff);
        
        movementX += Accelerometer.velocityX*Accelerometer.timeDiff;
        movementY += Accelerometer.velocityY*Accelerometer.timeDiff;
        movementZ += Accelerometer.velocityZ*Accelerometer.timeDiff;
        
        if (x < 0 )
        	movementX *= -1;
        if (y < 0 )
        	movementY *= -1;
        if (y < 0 )
        	movementZ *= -1;
        
        this.x += movementX;
        this.y += movementY;
        this.z += movementZ;        
        
        
        Accelerometer.velocityX = (Accelerometer.velocityX + x*Accelerometer.timeDiff);
    	Accelerometer.velocityY = (Accelerometer.velocityY + y*Accelerometer.timeDiff);
    	Accelerometer.velocityZ = (Accelerometer.velocityZ + z*Accelerometer.timeDiff);
    	

        //((TextView) findViewById(R.id.current_velocity_x)).setText(String.valueOf(this.velocityX));
        //((TextView) findViewById(R.id.current_velocity_y)).setText(String.valueOf(this.velocityY));
        //((TextView) findViewById(R.id.current_velocity_z)).setText(String.valueOf(this.velocityZ));
    }
    
    public void onDistanceChanged(float x, float y, float z ) {
    	   
    }
 
}