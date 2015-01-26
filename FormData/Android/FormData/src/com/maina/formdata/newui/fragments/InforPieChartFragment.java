package com.maina.formdata.newui.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.maina.formdata.R;
import com.maina.formdata.repository.IDFormRepository;
import com.maina.formdata.repository.IDFormResultRepository;
import com.maina.formdata.repository.Repositoryregistry;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

/**
 * Created by Patrick on 10/21/2014.
 */
public class InforPieChartFragment extends BaseFragment {

    private static int[] COLORS = new int[] { Color.GREEN, Color.BLUE, Color.MAGENTA, Color.CYAN };
    private CategorySeries mSeries = new CategorySeries("");
    private DefaultRenderer mRenderer = new DefaultRenderer();
    private GraphicalView mChartView;
    private ProgressDialog dialog;
    private GetTotals getTotals;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Form Response Status");
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.xy_chart, container, false);
        mRenderer.setZoomButtonsVisible(true);
        mRenderer.setStartAngle(180);
        mRenderer.setLabelsTextSize(24);
        mRenderer.setLabelsColor(Color.BLACK);
        mRenderer.setDisplayValues(true);
        getTotals = new GetTotals();
        if(getTotals.getStatus() == AsyncTask.Status.PENDING)
            getTotals.execute();
        return viewGroup;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mChartView == null) {
            LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.chart);
            mChartView = ChartFactory.getPieChartView(getActivity(), mSeries, mRenderer);
            mRenderer.setClickEnabled(true);
            mChartView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
                    if (seriesSelection == null) {
                        Toast.makeText(getActivity(), "No chart element selected", Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        for (int i = 0; i < mSeries.getItemCount(); i++) {
                            mRenderer.getSeriesRendererAt(i).setHighlighted(i == seriesSelection.getPointIndex());
                        }
                        mChartView.repaint();
                        Toast.makeText(getActivity(), " selected value: " + seriesSelection.getValue(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
            layout.addView(mChartView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.FILL_PARENT));
        } else {
            mChartView.repaint();
        }
    }

    class GetTotals extends AsyncTask<Void, Void, int[]> {

        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity());
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
            else if(dialog.isShowing())dialog.dismiss();
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
