package com.keruiyun.saike.controls;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;

public class HDStatusLineChart extends LineChart {
	private float _maxValue;
	private float _averageValue;

	public HDStatusLineChart(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		initView();
	}

	public HDStatusLineChart(Context context, AttributeSet attrs) {
		super(context, attrs);

		initView();
	}

	public HDStatusLineChart(Context context) {
		super(context);

		initView();
	}

	private void initView() {
		setDragDecelerationEnabled(false);
		setHighlightIndicatorEnabled(false);
		setBackgroundColor(Color.TRANSPARENT);
		setGridBackgroundColor(Color.TRANSPARENT);

		setScaleXEnabled(false);
		setScaleYEnabled(false);
		setDoubleTapToZoomEnabled(false);
		setScaleMinima(0, 0);

		getXAxis().setDrawGridLines(false);
		getXAxis().setDrawAxisLine(false);
		getXAxis().setDrawLabels(false);
		getXAxis().setLabelsToSkip(0);
		getXAxis().setTextColor(0xffffffff);
		getXAxis().setGridColor(0xffffffff);
		getXAxis().setGridLineWidth(1);
		getXAxis().setTextSize(12.0f);
		getXAxis().setScaleLine(true);
		getXAxis().setUnit("");
		getXAxis().setPosition(XAxisPosition.BOTTOM);
		getAxisLeft().setDrawGridLines(false);
		getAxisLeft().setDrawAxisLine(false);
		getAxisLeft().setDrawLabels(false);
		getAxisLeft().setXOffset(8);
		getAxisLeft().setAxisMaxValue(3000);
		getAxisLeft().setAxisMinValue(0);
		getAxisRight().setDrawGridLines(false);
		getAxisRight().setDrawAxisLine(false);
		getAxisRight().setDrawLabels(false);
		getLegend().setEnabled(false);

		getAxisLeft().removeAllLimitLines();
		getAxisLeft().setTextSize(16);

		initLines();
	}

	private void initLines() {
		addLine(0.0f, "");
		addLine(725.0f, "");
		addLine(1450.0f, "");
		addLine(2175.0f, "");
		addLine(2900.0f, "");
	}

	private void addLine(float value, String text) {
		LimitLine line = new LimitLine(value, text);
		line.setLabelPosition(LimitLabelPosition.POS_LEFT_BELOW);
		line.setLineWidth(2.0f);
		line.setLineColor(0xff006ae9);
		line.setTextSize(16);
		line.setTextColor(0xffffffff);

		getAxisLeft().addLimitLine(line);

		setDescription(null);
	}

	public void setDescription(String desc) {
		super.setDescription(desc);
	}

	public void setMaxValue(float value, String text) {
		_maxValue = value;

		if (_maxValue > 0) {
			LimitLine line = new LimitLine(_maxValue, "");
			line.setLineWidth(1);
			line.setLineColor(0xffe5e5e5);
			line.setTextSize(11);
			line.setTextColor(0xff3c3c3c);
			line.setLabel(text);

			getAxisLeft().addLimitLine(line);
		}
	}

	public void setAverageValue(float value, String text) {
		_averageValue = value;

		if (_averageValue > 0) {
			LimitLine line = new LimitLine(_averageValue, "");
			line.setLineWidth(1);
			line.setLineColor(0xffe5e5e5);
			line.setTextSize(11);
			line.setTextColor(0xff3c3c3c);
			line.setLabel(text);

			getAxisLeft().addLimitLine(line);
		}
	}
}
