package com.axon.ars;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class MyEditText extends EditText {
	private Drawable imgCloseButton = getResources().getDrawable(
			R.drawable.ic_action_cancel);

	public MyEditText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public MyEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public MyEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		imgCloseButton.setBounds(0, 0, imgCloseButton.getIntrinsicWidth(),
				imgCloseButton.getIntrinsicHeight());
		handleClearButton();
		this.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				MyEditText et = MyEditText.this;

				if (et.getCompoundDrawables()[2] == null)
					return false;

				if (event.getAction() != MotionEvent.ACTION_UP)
					return false;

				if (event.getX() > et.getWidth() - et.getPaddingRight()
						- imgCloseButton.getIntrinsicWidth()) {
					et.setText("");
					MyEditText.this.handleClearButton();
				}
				return false;
			}
		});

		this.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				MyEditText.this.handleClearButton();

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void setTypeface(Typeface tf, int style) {
		if (style == Typeface.BOLD) {
			super.setTypeface(Typeface.createFromAsset(
					getContext().getAssets(), "fonts/Vegur-B 0.602.otf"));
		} else {
			super.setTypeface(Typeface.createFromAsset(
					getContext().getAssets(), "fonts/Vegur-R 0.602.otf"));
		}
	}

	private void handleClearButton() {
		// TODO Auto-generated method stub
		if (this.getText().toString().equals("")) {
			// add the clear button
			this.setCompoundDrawables(this.getCompoundDrawables()[0],
					this.getCompoundDrawables()[1], null,
					this.getCompoundDrawables()[3]);
		} else {
			// remove clear button
			this.setCompoundDrawables(this.getCompoundDrawables()[0],
					this.getCompoundDrawables()[1], imgCloseButton,
					this.getCompoundDrawables()[3]);
		}

	}

}