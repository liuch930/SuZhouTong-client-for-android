package com.hua.goddess.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hua.goddess.R;

public class BusTransferFragment extends Fragment implements OnClickListener {
	private ListView listview;
	private TextView bus_help;
	private Button switchStartEnd;
	private EditText startEdit;
	private EditText targetEdit;
	private ImageView startClearButton;
	private ImageView targetClearButton;
	private Context context;
	private InputMethodManager localInputMethodManager;
	private Button search;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
		localInputMethodManager = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.bus_transfer_fragment, null);
		listview = (ListView) rootView.findViewById(R.id.listview);
		bus_help = (TextView) rootView.findViewById(R.id.bus_help_text);
		switchStartEnd = (Button) rootView
				.findViewById(R.id.bus_switch_start_target);
		search = (Button) rootView.findViewById(R.id.bus_switch_search);
		startEdit = (EditText) rootView.findViewById(R.id.bus_start_edit);
		startClearButton = (ImageView) rootView
				.findViewById(R.id.bus_start_clear);
		targetEdit = (EditText) rootView.findViewById(R.id.bus_target_edit);
		targetClearButton = (ImageView) rootView
				.findViewById(R.id.bus_target_clear);

		initView();
		return rootView;
	}

	private void initView() {
		bus_help.setText(R.string.bus_text_switch);
		switchStartEnd.setOnClickListener(this);
		startClearButton.setOnClickListener(this);
		targetClearButton.setOnClickListener(this);
		search.setOnClickListener(this);
		startEdit.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString().length() > 0) {
					if (startClearButton.getVisibility() == View.GONE) {
						startClearButton.setVisibility(View.VISIBLE);
					} 
				} else {
					if (startClearButton.getVisibility() == View.VISIBLE) {
						startClearButton.setVisibility(View.GONE);
					} 
				}
			}
		});
		targetEdit.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString().length() > 0) {
					if (targetClearButton.getVisibility() == View.GONE) {
						targetClearButton.setVisibility(View.VISIBLE);
					} 
				} else {
					if (targetClearButton.getVisibility() == View.VISIBLE) {
						targetClearButton.setVisibility(View.GONE);
					} 
				}
			}
		});
	}

	private void hideEditTextIME(EditText paramEditText) {
		if (localInputMethodManager == null)
			return;
		localInputMethodManager.hideSoftInputFromWindow(
				paramEditText.getWindowToken(), 0);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bus_switch_start_target:
			String s  = startEdit.getText().toString().trim();
			startEdit.setText(targetEdit.getText().toString().trim());
			targetEdit.setText(s);
			break;
		case R.id.bus_start_clear:
			startEdit.setText("");
			break;
		case R.id.bus_target_clear:
			targetEdit.setText("");
			break;
		case R.id.bus_switch_search:
			switchSearch();
			break;
		}

	}

	private void switchSearch() {
		String startStr = startEdit.getText().toString().trim();
		String targeStr = targetEdit.getText().toString().trim();
		if(startStr == null || startStr.length() < 1){
			Toast.makeText(context, R.string.import_start, Toast.LENGTH_SHORT).show();
			return;
		}
		if(targeStr == null || targeStr.length() < 1){
			Toast.makeText(context, R.string.import_end, Toast.LENGTH_SHORT).show();
			return;
		}
	}

}
