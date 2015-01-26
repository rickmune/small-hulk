package com.maina.formdata.ui;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.maina.formdata.R;
import com.maina.formdata.repository.IDFormRepository;
import com.maina.formdata.repository.IDFormResultRepository;
import com.maina.formdata.repository.Repositoryregistry;
import com.maina.formdata.ui.InforActivity.GetTotals;

public class InforGraphActivity extends BaseActivity{

private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("InforActivity","onCreate");
		super.onCreate(savedInstanceState);
		setTitle("Response Infor");
		setContentView(R.layout.reports);
		new GetTotals().execute();
	}
	
	class GetTotals extends AsyncTask<Void, Void, int[]> {

		protected void onPreExecute() {
			dialog = new ProgressDialog(InforGraphActivity.this);
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
			((TextView)findViewById(R.id.total_q)).setText("No. of Forms ("+ returns[0] + ")");
			barGraph(returns);
			/*((TextView)findViewById(R.id.n_o_f)).setText("("+returns[0] + ") Forms");
			((TextView)findViewById(R.id.t_r)).setText("("+returns[1] + ") Total Responses");
			((TextView)findViewById(R.id.s_r)).setText("("+returns[2] + ") Sent Responses");
			((TextView)findViewById(R.id.u_s)).setText("("+returns[3] + ") Unsent Responses");*/
		}
		
		private void barGraph(int[] returns){
			GraphView.GraphViewData [] data = new GraphView.GraphViewData [returns.length];
			for(int x = 0; x < data.length; x++){
				data[x] = new GraphView.GraphViewData(x, returns[x]);
			}
	        GraphViewSeries exampleSeries = new GraphViewSeries(data);

	        GraphView graphView = new BarGraphView(InforGraphActivity.this, "Response Infor" );
	        graphView.addSeries(exampleSeries); // data
	        graphView.getGraphViewStyle().setNumHorizontalLabels(3);
	        graphView.getGraphViewStyle().setGridColor(Color.GREEN);
	        graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.BLUE);
	        graphView.setHorizontalLabels(new String[] {"Total", "Sent", "Unsent"});
	        LinearLayout layout = (LinearLayout) findViewById(R.id.graph1);
	        layout.addView(graphView);
	    }
		
	}
}
