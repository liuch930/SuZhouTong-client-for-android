package com.hua.goddess.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hua.goddess.R;
import com.hua.goddess.activites.ContentActivity;
import com.hua.goddess.activites.ContentActivity.CloseSoftKeyboardListener;
import com.hua.goddess.activites.WebViewActivity;
import com.hua.goddess.base.communicate.GetChatInterface;
import com.hua.goddess.vo.ChatMessageVO;

public class ChatSecretaryFragment extends Fragment implements
		CloseSoftKeyboardListener {
	private static View currentView = null;
	private ListView cListView;
	private EditText cEditText;
	private Button cButton;
	private ArrayList<ChatMessageVO> listChat = new ArrayList<ChatMessageVO>();
	private Context context;
	private double currentTime = 0, oldTime = 0;
	private ChatMessAdapter cAdapter;
	private InputMethodManager imm;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		imm = (InputMethodManager) getActivity().getSystemService(
				Context.INPUT_METHOD_SERVICE);
		if (currentView != null) {
			ViewGroup parent = (ViewGroup) currentView.getParent();
			if (parent != null)
				parent.removeView(currentView);
		}
		try {
			currentView = inflater.inflate(R.layout.fragment_chat, container,
					false);
		} catch (InflateException e) {
			/* map is already there, just return view as it is */
			e.printStackTrace();
		}
		initView();
		return currentView;
	}

	private void initView() {
		cListView = (ListView) currentView.findViewById(R.id.chat_listview);
		cEditText = (EditText) currentView.findViewById(R.id.chat_editmessage);
		cButton = (Button) currentView.findViewById(R.id.chat_send);
		cButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sendMessage();
			}
		});
		addChatVo(true, getRandomWelcomeTips());
		cAdapter = new ChatMessAdapter();
		cListView.setAdapter(cAdapter);
		((ContentActivity) context).registerMyTouchListener(this);
	}

	private String getRandomWelcomeTips() {
		String[] chat_array = this.getResources().getStringArray(
				R.array.chat_tips);
		int index = (int) (Math.random() * (chat_array.length - 1));
		return chat_array[index];
	}

	private void sendMessage() {
		getTime();
		String content_str = cEditText.getText().toString().replace(" ", "")
				.replace("\n", "").trim();
		if (!TextUtils.isEmpty(content_str)) {
			cEditText.setText("");
			addChatVo(false, content_str);
			cAdapter.notifyDataSetChanged();
			cListView.setSelection(listChat.size());
			ChatTask task = new ChatTask();
			task.execute(content_str);
		} else {
			Toast.makeText(context, "请输入内容！", Toast.LENGTH_SHORT).show();
		}

	}

	public void addChatVo(boolean ifComMeg, String content) {
		ChatMessageVO vo = new ChatMessageVO();
		vo.setComMeg(ifComMeg);
		vo.setDate(getTime());
		vo.setText(content);
		listChat.add(vo);
	}

	class ChatTask extends AsyncTask<String, Void, ChatMessageVO> {

		@Override
		protected ChatMessageVO doInBackground(String... params) {
			ChatMessageVO vo = null;
			try {
				vo = GetChatInterface.getNetData(params[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return vo;
		}

		@Override
		protected void onPostExecute(ChatMessageVO result) {
			super.onPostExecute(result);
			if (result != null) {
				result.setDate(getTime());
				result.setComMeg(true);
				listChat.add(result);
				cAdapter.notifyDataSetChanged();
				cListView.setSelection(listChat.size());
			}
		}

	}

	class ChatMessAdapter extends BaseAdapter {
		ChatItemHolder cHolder;
		final int TYPE_LEFT = 0;
		final int TYPE_RIGHT = 1;

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listChat.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return listChat.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public int getItemViewType(int position) {
			// TODO Auto-generated method stub
			return listChat.get(position).isComMeg() ? TYPE_LEFT : TYPE_RIGHT;
		}

		@Override
		public int getViewTypeCount() {
			// TODO Auto-generated method stub
			return 2;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			int type = getItemViewType(position);
			if (convertView == null) {
				switch (type) {
				case TYPE_LEFT:
					convertView = LayoutInflater.from(context).inflate(
							R.layout.chatting_item_msg_text_left, null);
					cHolder = new ChatItemHolder();
					cHolder.sendtime = (TextView) convertView
							.findViewById(R.id.tv_sendtime);
					cHolder.chatcontent = (TextView) convertView
							.findViewById(R.id.tv_chatcontent);
					convertView.setTag(cHolder);
					break;
				case TYPE_RIGHT:
					convertView = LayoutInflater.from(context).inflate(
							R.layout.chatting_item_msg_text_right, null);
					cHolder = new ChatItemHolder();
					cHolder.chatcontent = (TextView) convertView
							.findViewById(R.id.tv_chatcontent);
					convertView.setTag(cHolder);
					break;
				default:
					break;
				}
			} else {
				switch (type) {
				case TYPE_LEFT:
					cHolder = (ChatItemHolder) convertView.getTag();
					break;
				case TYPE_RIGHT:
					cHolder = (ChatItemHolder) convertView.getTag();
					break;
				default:
					break;
				}
			}
			switch (type) {
			case TYPE_LEFT:
				if (!TextUtils.isEmpty(listChat.get(position).getDate())) {
					cHolder.sendtime.setVisibility(View.VISIBLE);
					cHolder.sendtime.setText(listChat.get(position).getDate());
				} else {
					cHolder.sendtime.setVisibility(View.GONE);
				}
				break;
			}
			if (!TextUtils.isEmpty(listChat.get(position).getUrl())) {
				cHolder.chatcontent.setText(listChat.get(position).getText()
						+ "，点我查看哦！");
				cHolder.chatcontent.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						Intent intent = new Intent(context,
								WebViewActivity.class);
						intent.putExtra("url", listChat.get(position).getUrl());
						context.startActivity(intent);

					}
				});
			} else {
				cHolder.chatcontent.setText(listChat.get(position).getText());
				cHolder.chatcontent.setOnClickListener(null);
			}

			return convertView;
		}
	}

	class ChatItemHolder {
		TextView sendtime;
		TextView chatcontent;
	}

	private String getTime() {
		currentTime = System.currentTimeMillis();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date curDate = new Date();
		String str = format.format(curDate);
		if (currentTime - oldTime >= 500) {
			oldTime = currentTime;
			return str;
		} else {
			return "";
		}

	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		((ContentActivity) context).unRegisterMyTouchListener(this);
	}

	@Override
	public void onCloseListener() {
		imm.hideSoftInputFromWindow(cEditText.getWindowToken(), 0);
	}
}
