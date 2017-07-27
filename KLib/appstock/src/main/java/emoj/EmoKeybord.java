package emoj;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.kfd.activityfour.R;
import com.kfd.common.EmojiUtil;

import emoj.EmoticonsGridAdapter.KeyClickListener;

public class EmoKeybord extends PopupWindow implements KeyClickListener
{
	public static final int NO_OF_EMOTICONS = 40;
	public static final int START_INDEX = 100;

	private View popUpView;

	private FragmentActivity mActivity;

	private int keyboardHeight;

	private Bitmap[] emoticons;

	private View emoticonsButton;

	private boolean isKeyBoardVisible;

	private ViewGroup parentLayout;

	private LinearLayout mEmoticonsCover;
	
	public EmoKeybord(FragmentActivity activity, ViewGroup parentLayout, LinearLayout emoticonsCover, View emoticonsButton, EditText content)
	{
		super(activity);
		mActivity = activity;
		
	    ColorDrawable dw = new ColorDrawable(0xb0000000);
        setBackgroundDrawable(dw);

		setEmoticonsButton(emoticonsButton);

		mContent = content;

		this.parentLayout = parentLayout;

		this.mEmoticonsCover = emoticonsCover;

		popUpView = LayoutInflater.from(activity).inflate(R.layout.emoticons_popup, null);

		ViewPager pager = (ViewPager) popUpView.findViewById(R.id.emoticons_pager);
		pager.setOffscreenPageLimit(3);

		ArrayList<String> paths = new ArrayList<String>();

		for (short i = 1; i <= NO_OF_EMOTICONS; i++)
		{
			paths.add((i + START_INDEX - 1) + ".png");
		}

		// Defining default height of keyboard which is equal to 230 dip
		final float popUpheight = activity.getResources().getDimension(R.dimen.keyboard_height);
		changeKeyboardHeight((int) popUpheight);

		EmoticonsPagerAdapter adapter = new EmoticonsPagerAdapter(mActivity, paths, this);
		pager.setAdapter(adapter);

		TextView backSpace = (TextView) popUpView.findViewById(R.id.back);
		backSpace.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
				if (mContent != null)
				{
//					String content = mContent.getText().toString();
					int cursorPosition = mContent.getSelectionStart();
					
					String content = mContent.getText().toString();
					
					if(cursorPosition != 0 && content.substring(cursorPosition - 1, cursorPosition).equals("￼"))
					{
						@SuppressWarnings("unchecked")
						ArrayList<String> list = (ArrayList<String>) mContent.getTag();
						
						int j = 0;
						for(int i =0; i < content.length(); i++)
						{
							
							String a = content.toString().substring(i, i + 1);
							if(a.equals("￼"))
							{
								if(i + 1 == cursorPosition)
								{
									list.remove(j);
									mContent.setTag(list);
									break;
								}
								j ++;
							}
							
						}
						
						
					}
					
					mContent.dispatchKeyEvent(event);
				}
			}
		});

		setOnDismissListener(new OnDismissListener()
		{
			@Override
			public void onDismiss()
			{
				mEmoticonsCover.setVisibility(LinearLayout.GONE);
			}
		});

		readEmoticons();
		
		checkKeyboardHeight(parentLayout);

		setContentView(popUpView);
		setHeight(keyboardHeight);
		setWidth(LayoutParams.MATCH_PARENT);
		setFocusable(false);

	}

	/**
	 * change height of emoticons keyboard according to height of actual
	 * keyboard
	 * 
	 * @param height
	 *            minimum height by which we can make sure actual keyboard is
	 *            open or not
	 */
	private void changeKeyboardHeight(int height)
	{
		if (height > 100)
		{
			keyboardHeight = height;
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, keyboardHeight);
			if(mEmoticonsCover != null)
			{
				mEmoticonsCover.setLayoutParams(params);
			}
		}

	}

	/**
	 * Reading all emoticons in local cache
	 */
	private void readEmoticons()
	{

		emoticons = new Bitmap[NO_OF_EMOTICONS];
		for (short i = 0; i < NO_OF_EMOTICONS; i++)
		{
			emoticons[i] = getImage(mActivity, (i + START_INDEX));
		}
	}
	
	public Bitmap[] getEmoticons()
	{
		return emoticons;
	}

	public void setContent(EditText content)
	{
		this.mContent = content;
	}

	/**
	 * For loading smileys from assets
	 */
	public static Bitmap getImage(Context context, int index)
	{
		String path = index + ".png";
		AssetManager mngr = context.getAssets();
		InputStream in = null;
		try
		{
			in = mngr.open("emoticons/" + path);
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		Bitmap temp = BitmapFactory.decodeStream(in, null, null);
		return temp;
	}

	@Override
	public void keyClickedIndex(final String index)
	{
		if (mContent != null)
		{
			ImageGetter imageGetter = new ImageGetter() {
				public Drawable getDrawable(String source) {
					Drawable d = new BitmapDrawable(mActivity.getResources(), emoticons[Integer.parseInt(source.replace(".png", "")) - 100]);
					d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
					return d;
				}
			};
			
			int cursorPosition = mContent.getSelectionStart();
			
			Spanned s = Html.fromHtml(EmojiUtil.convertTag("[face:" + index.replace(".png", "") + "]"), imageGetter, null);
			
			mContent.getText().insert(cursorPosition, s);
			if(mContent.getTag() == null)
			{
				mContent.setTag(new ArrayList<String>());
			}
			
			@SuppressWarnings("unchecked")
			ArrayList<String> list = (ArrayList<String>) mContent.getTag();
			
			int j = 0;
			for(int i =0; i < mContent.getText().toString().length(); i++)
			{
				String a = mContent.getText().toString().substring(i, i + 1);
				if(a.equals("￼"))
				{
					if(i == cursorPosition)
					{
						list.add(j, "[face:" + index.replace(".png", "") + "]");
					}
					j ++;
				}
			}
		}
	}
	
	private EditText mContent;

	/**
	 * Checking keyboard height and keyboard visibility
	 */
	int previousHeightDiffrence = 0;

	private void checkKeyboardHeight(final View parentLayout)
	{

		if(parentLayout == null)
		{
			return;
		}
		
		parentLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
		{
			@Override
			public void onGlobalLayout()
			{

				Rect r = new Rect();
				parentLayout.getWindowVisibleDisplayFrame(r);

				int screenHeight = parentLayout.getRootView().getHeight();
				int heightDifference = screenHeight - (r.bottom);

				if (previousHeightDiffrence - heightDifference > 50)
				{
					dismiss();
				}

				previousHeightDiffrence = heightDifference;
				if (heightDifference > 100)
				{

					isKeyBoardVisible = true;
					changeKeyboardHeight(heightDifference);

				} else
				{
					isKeyBoardVisible = false;
				}
			}
		});

	}

	private void setEmoticonsButton(View emoticonsButton)
	{
		if (this.emoticonsButton != null)
		{
			this.emoticonsButton.setOnClickListener(null);
		}

		this.emoticonsButton = emoticonsButton;

		if (emoticonsButton == null)
		{
			return;
		}

		emoticonsButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{

				if (!EmoKeybord.this.isShowing())
				{

					EmoKeybord.this.setHeight((int) (keyboardHeight));

					if (isKeyBoardVisible)
					{
						mEmoticonsCover.setVisibility(LinearLayout.GONE);

					} else
					{
						mEmoticonsCover.setVisibility(LinearLayout.VISIBLE);
					}
					EmoKeybord.this.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0);

				} else
				{
					EmoKeybord.this.dismiss();
				}

			}
		});

	}
}
