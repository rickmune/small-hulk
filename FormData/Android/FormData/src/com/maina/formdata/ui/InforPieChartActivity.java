package com.maina.formdata.ui;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.maina.formdata.R;
import com.maina.formdata.repository.IDFormRepository;
import com.maina.formdata.repository.IDFormResultRepository;
import com.maina.formdata.repository.Repositoryregistry;

public class InforPieChartActivity extends BaseActivity {

	  private static int[] COLORS = new int[] { Color.GREEN, Color.BLUE, Color.MAGENTA, Color.CYAN };
	  private CategorySeries mSeries = new CategorySeries("");
	  private DefaultRenderer mRenderer = new DefaultRenderer();
	  private GraphicalView mChartView;
	  private ProgressDialog dialog;
	  private GetTotals getTotals;
	  
	  @Override
	  protected void onRestoreInstanceState(Bundle savedState) {
	    super.onRestoreInstanceState(savedState);
	    mSeries = (CategorySeries) savedState.getSerializable("current_series");
	    mRenderer = (DefaultRenderer) savedState.getSerializable("current_renderer");
	  }

	  @Override
	  protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    outState.putSerializable("current_series", mSeries);
	    outState.putSerializable("current_renderer", mRenderer);
	  }
	  
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.xy_chart);
	   
	    mRenderer.setZoomButtonsVisible(true);
	    mRenderer.setStartAngle(180);
	    mRenderer.setLabelsTextSize(24);
	    mRenderer.setLabelsColor(Color.BLACK);	    
	    mRenderer.setDisplayValues(true);
	    getTotals = new GetTotals();
	    if(getTotals.getStatus() == Status.PENDING)
	    	getTotals.execute();
	    
	  }
	  
	  @Override
	  protected void onResume() {
	    super.onResume();
	    if (mChartView == null) {
	      LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
	      mChartView = ChartFactory.getPieChartView(this, mSeries, mRenderer);
	      mRenderer.setClickEnabled(true);
	      mChartView.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	          SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
	          if (seriesSelection == null) {
	            Toast.makeText(InforPieChartActivity.this, "No chart element selected", Toast.LENGTH_SHORT)
	                .show();
	          } else {
	            for (int i = 0; i < mSeries.getItemCount(); i++) {
	              mRenderer.getSeriesRendererAt(i).setHighlighted(i == seriesSelection.getPointIndex());
	            }
	            mChartView.repaint();
	            Toast.makeText(InforPieChartActivity.this, " selected value: " + seriesSelection.getValue(), 
	            		Toast.LENGTH_SHORT).show();
	          }
	        }
	      });
	      layout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,
	          LayoutParams.FILL_PARENT));
	    } else {
	      mChartView.repaint();
	    }
	  }
	  
	  class GetTotals extends AsyncTask<Void, Void, int[]> {

			protected void onPreExecute() {
				dialog = new ProgressDialog(InforPieChartActivity.this);
				dialog.setMessage("Calculating...");
				dialog.setIndeterminate(true);
				dialog.setCancelable(true);
				dialog.show();
			}
			
			@Override
			protected int[] doInBackground(Void... params) {
				int [] status = new int [4];
				try {
					status[0] = Repositoryregistry.get(IDFormRepository.class, dataManager)
							.getFormCount();
				} catch (Exception e) { e.printStackTrace();}
				try {
					int[] s = Repositoryregistry.get(IDFormResultRepository.class, dataManager)
							.getStatusNumbers();
					status[1] = s[0];
					status[2] = s[1];
					status[3] = s[2];
				} catch (Exception e) { e.printStackTrace(); }
				return status;
			}
			
			@Override
			protected void onPostExecute(int[] returns) {
				if(dialog == null)return;
				else if(dialog != null && dialog.isShowing())dialog.dismiss();
				barGraph(returns);
			}
			
			private void barGraph(int[] returns){
				String [] values = new String []{"Total", "Sent", "Unsent"};
				System.out.println("returns: " + returns.length + " values: " + values.length);
				for(int x = 1; x < returns.length; x++){
					mSeries.add(values[x - 1], returns[x]);
					SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
			        renderer.setColor(COLORS[(mSeries.getItemCount() - 1) % COLORS.length]);
			        mRenderer.addSeriesRenderer(renderer);
				}
				mChartView.repaint();
		    }
			
		}
}
