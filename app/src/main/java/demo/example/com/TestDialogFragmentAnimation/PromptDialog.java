package demo.example.com.TestDialogFragmentAnimation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import demo.example.com.TestDialogFragmentAnimation.R;

public class PromptDialog extends DialogFragment {
	private Button mbtnOk;

	private int             mRequestCode;
	private DismissListener mDismissListener;

    public static interface DismissListener {
        public void onDialogDismiss(int requestCode, int resultCode);
    }

    public static final PromptDialog getInstance(int requestCode, DismissListener listener) {
		PromptDialog dlg = new PromptDialog();

        dlg.mRequestCode = requestCode;
		dlg.mDismissListener = listener;

		return dlg;
	}

	private PromptDialog() {
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		getDialog().setCanceledOnTouchOutside(false);
		setCancelable(true);

		// 背景透明，只有动画时才能看到，如对话框下陷上弹前，背景只是对话框本身大小且在将要显示的位置
		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

		View viewInterface = inflater.inflate(R.layout.prompt_dialog, null);

		mbtnOk = (Button)viewInterface.findViewById(R.id.dlg_ok);

		mbtnOk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dismiss();

                if (mDismissListener != null) {
                    mDismissListener.onDialogDismiss(mRequestCode, Activity.RESULT_OK);
                }
            }
        });

		// DialogFragment 目前我只发现用如下方法在此事件中可以实现弹出时的动画
		final Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.guide_save_succ_dlg_enter);
		viewInterface.startAnimation(anim);

		return viewInterface;
	}

	@Override
	public void onStart() {
		super.onStart();

		// 修改对话框的大小要在此事件中，不然有些机型会显示异常
		getDialog().getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		if (mDismissListener != null) {
			mDismissListener.onDialogDismiss(mRequestCode, Activity.RESULT_CANCELED);
		}

		super.onCancel(dialog);
	}
}
