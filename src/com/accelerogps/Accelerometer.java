package com.accelerogps;

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
	
	float velocityX=0;
	float velocityY=0;
	float velocityZ=0;
 
    private static Context CONTEXT;
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        CONTEXT = this;
    }
 
    protected void onResume() {
        super.onResume();
        if (AccelerometerManager.isSupported()) {
            AccelerometerManager.startListening(this);
        }
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
 
    /**
     * onShake callback
     */
    public void onShake(float force) {
        Toast.makeText(this, "Phone shaked : " + force, 1000).show();
    }
 
    public static float timeDiff;
    
    public float x;
    public float y;
    public float z;
    /**
     * onAccelerationChanged callback
     */
    public void onAccelerationChanged(float x, float y, float z) {
    	x = (float) Math.round(x*100)/100;
    	y = (float) Math.round(y*100)/100;
    	z = (float) Math.round(z*100)/100;
        ((TextView) findViewById(R.id.x)).setText(String.valueOf(x));
        ((TextView) findViewById(R.id.y)).setText(String.valueOf(y));
        ((TextView) findViewById(R.id.z)).setText(String.valueOf(z));
        float movementX = (float) (0.5 * x * x);
        float movementY = (float) (0.5 * y * y);
        float movementZ = (float) (0.5 * z * z);
        movementX += this.velocityX*Accelerometer.timeDiff;
        movementY += this.velocityY*Accelerometer.timeDiff;
        movementZ += this.velocityZ*Accelerometer.timeDiff;
        
        if (x < 0 )
        	movementX *= -1;
        if (y < 0 )
        	movementY *= -1;
        if (y < 0 )
        	movementZ *= -1;
        
        this.x += Math.round(movementX*100)/100;
        this.y += Math.round(movementY*100)/100;
        this.z += Math.round(movementZ*100)/100;
        
        ((TextView) findViewById(R.id.distx_t)).setText(String.valueOf(this.x));
        ((TextView) findViewById(R.id.disty_t)).setText(String.valueOf(this.y));
        ((TextView) findViewById(R.id.distz_t)).setText(String.valueOf(this.z));
        
        this.velocityX = this.velocityX + x*Accelerometer.timeDiff;
    	this.velocityY = this.velocityY + y*Accelerometer.timeDiff;
    	this.velocityZ = this.velocityZ + z*Accelerometer.timeDiff;
    }
    
    public void onDistanceChanged(float x, float y, float z ) {
    	   
    }
 
}