package com.mob.secverify.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.MobSDK;
import com.mob.PrivacyPolicy;
import com.mob.secverify.CustomUIRegister;
import com.mob.secverify.CustomViewClickListener;
import com.mob.secverify.GetTokenCallback;
import com.mob.secverify.OAuthPageEventCallback;
import com.mob.secverify.PageCallback;
import com.mob.secverify.PreVerifyCallback;
import com.mob.secverify.SecVerify;
import com.mob.secverify.UiLocationHelper;
import com.mob.secverify.datatype.VerifyResult;
import com.mob.secverify.demo.entity.LoginResult;
import com.mob.secverify.demo.exception.DemoException;
import com.mob.secverify.demo.login.LoginTask;
import com.mob.secverify.demo.ui.component.CommonProgressDialog;
import com.mob.secverify.demo.ui.component.PrivacyDialog;
import com.mob.secverify.demo.util.Const;
import com.mob.secverify.demo.util.CustomizeUtils;
import com.mob.secverify.exception.VerifyErr;
import com.mob.secverify.exception.VerifyException;
import com.mob.secverify.ui.AgreementPage;
import com.mob.tools.utils.ResHelper;
import com.mob.tools.utils.SharePrefrenceHelper;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;


public class MainActivity extends Activity implements View.OnClickListener {
	private static final String TAG = "MainActivity";
	private static final int REQUEST_CODE = 1001;
	private GifImageView logoIv;
	private Button verifyBtn;
	private Button verifyDialogBtn;
	private TextView versionTv;
	private TextView appNameTv;
	private boolean devMode = false;
	private int defaultUi = 0;
	private boolean isFirstTime = true;
	private SharePrefrenceHelper sharePrefrenceHelper;
	private boolean isVerifySupport = false;
	private boolean isPreVerifyDone = true;
	private long starttime;
	String mobToken;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CrashReport.initCrashReport(getApplicationContext(), "28fe0803ee", false);
		setContentView(R.layout.activity_main);
		if (Build.VERSION.SDK_INT >= 21){
			// ????????????????????????
			View decorView = getWindow().getDecorView();
			int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
					| View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
			decorView.setSystemUiVisibility(option);
//			 ?????????????????????
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			getWindow().setStatusBarColor(Color.TRANSPARENT);
			if (Build.VERSION.SDK_INT >= 23){
				getWindow().getDecorView().setSystemUiVisibility(
						View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
			}
		}

		initView();
		checkPermissions();
		// ??????????????????????????????????????????????????????????????????????????????
//		showMobPrivacy();
		showMobPrivacyDirect();
		isVerifySupport = SecVerify.isVerifySupport();
		if (isVerifySupport) {
			preVerify();
		} else {
			Toast.makeText(this, "?????????????????????", Toast.LENGTH_SHORT).show();
		}
	}

	private void showMobPrivacyDirect() {
		sharePrefrenceHelper = new SharePrefrenceHelper(MobSDK.getContext());
		sharePrefrenceHelper.open("privacy", 1);

		if (sharePrefrenceHelper.getBoolean("isGrant")) {
			return;
		}

		submitPolicyGrantResult(true);
		sharePrefrenceHelper.putBoolean("isGrant", true);
	}

	private void submitPolicyGrantResult(boolean grantResult) {
		MobSDK.submitPolicyGrantResult(grantResult, null);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
			case R.id.sec_verify_demo_main_verify: {
				// ?????????????????????
				// ?????????UI
				if (!isPreVerifyDone) {
					Toast.makeText(MainActivity.this, "????????????????????????" , Toast.LENGTH_SHORT).show();
					return;
				}
				addCustomView();
				customizeUi();
//				SecVerify.setAdapterFullName("com.mob.secverify.demo.ui.component.MyAdapter");
				SecVerify.autoFinishOAuthPage(false);
				isVerifySupport = SecVerify.isVerifySupport();
				if (isVerifySupport) {
					verify();
				} else {
					Toast.makeText(this, "?????????????????????", Toast.LENGTH_SHORT).show();
				}
				break;
			}
			case R.id.sec_verify_demo_main_verify_dialog: {
				if (!isPreVerifyDone) {
					Toast.makeText(MainActivity.this, "????????????????????????" , Toast.LENGTH_SHORT).show();
					return;
				}
				addCustomView4();
				customizeUi4();
//				SecVerify.setAdapterFullName("com.mob.secverify.demo.ui.component.DialogAdapter");
				SecVerify.autoFinishOAuthPage(false);
				isVerifySupport = SecVerify.isVerifySupport();
				if (isVerifySupport) {
					verify();
				} else {
					Toast.makeText(this, "?????????????????????", Toast.LENGTH_SHORT).show();
				}
				break;
			}
			case R.id.sec_verify_demo_main_app_name: {
				switchDevMode();
				break;
			}
			case R.id.sec_verify_demo_main_version: {
				switchDevMode();
				break;
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE) {
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		// Demo????????????????????????????????????????????????????????????
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void initView() {
		logoIv = findViewById(R.id.sec_verify_demo_main_logo);
		verifyBtn = findViewById(R.id.sec_verify_demo_main_verify_dialog);
		verifyDialogBtn = findViewById(R.id.sec_verify_demo_main_verify);
		appNameTv = findViewById(R.id.sec_verify_demo_main_app_name);
		versionTv = findViewById(R.id.sec_verify_demo_main_version);
		versionTv.setText(SecVerify.getVersion());

		logoIv.setImageResource(R.drawable.sec_verify_demo_tradition);
		verifyBtn.setOnClickListener(this);
		verifyDialogBtn.setOnClickListener(this);
		logoIv.setOnClickListener(this);
		appNameTv.setOnClickListener(this);
		versionTv.setOnClickListener(this);
	}

	/* ?????????????????? */
	private void checkPermissions() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			try {
				PackageManager pm = getPackageManager();
				PackageInfo pi = pm.getPackageInfo(getPackageName(), PackageManager.GET_PERMISSIONS);
				ArrayList<String> list = new ArrayList<String>();
				for (String p : pi.requestedPermissions) {
					if (checkSelfPermission(p) != PackageManager.PERMISSION_GRANTED) {
						list.add(p);
					}
				}
				if (list.size() > 0) {
					String[] permissions = list.toArray(new String[list.size()]);
					if (permissions != null) {
						requestPermissions(permissions, 1);
					}
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}

	/**
	 * ?????????
	 * <p>
	 * ???????????????????????????????????????????????????????????????????????????????????????
	 */
	private void preVerify() {

		isPreVerifyDone = false;
		//?????????1000-10000??????
		SecVerify.setTimeOut(5000);
		//?????????debug tag ???CMCC-SDK,?????????CT_ ?????????PriorityAsyncTask
		SecVerify.setDebugMode(true);
		SecVerify.setUseCache(true);
		SecVerify.preVerify(new PreVerifyCallback() {
			@Override
			public void onComplete(Void data) {
				// Nothing to do
				if (devMode) {
					Toast.makeText(MainActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
				}
				isPreVerifyDone = true;
			}

			@Override
			public void onFailure(VerifyException e) {
				isPreVerifyDone = true;
				// Nothing to do
				Throwable t = e.getCause();
				String errDetail = null;
				if (t != null){
					errDetail = t.getMessage();
				}

				if (devMode) {
					// ????????????
					Log.e(TAG, "preVerify failed", e);
					// ?????????
					int errCode = e.getCode();
					// ????????????
					String errMsg = e.getMessage();
					// ??????????????????????????????????????????t?????????????????????t????????????null
					String msg = "?????????: " + errCode + "\n????????????: " + errMsg;
					if (!TextUtils.isEmpty(errDetail)) {
						msg += "\n????????????: " + errDetail;
					}
					Log.e(TAG,msg);
					Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	/**
	 * ????????????
	 */
	private void verify() {
		CommonProgressDialog.showProgressDialog(this);
		//?????????verify????????????
		SecVerify.OtherOAuthPageCallBack(new OAuthPageEventCallback() {
			@Override
			public void initCallback(OAuthPageEventResultCallback cb) {
				cb.pageOpenCallback(new PageOpenedCallback() {
					@Override
					public void handle() {
						Log.i(TAG, System.currentTimeMillis() + " pageOpened");
						Log.e(TAG,(System.currentTimeMillis() - starttime) + "ms is the time pageOpen take ");
					}
				});
				cb.loginBtnClickedCallback(new LoginBtnClickedCallback() {
					@Override
					public void handle() {
						Log.i(TAG, System.currentTimeMillis() + " loginBtnClicked");
					}
				});
				cb.agreementPageClosedCallback(new AgreementPageClosedCallback() {
					@Override
					public void handle() {
						Log.i(TAG, System.currentTimeMillis() + " agreementPageClosed");
					}
				});
				cb.agreementPageOpenedCallback(new AgreementClickedCallback() {
					@Override
					public void handle() {
						Log.i(TAG, System.currentTimeMillis() + " agreementPageOpened");
					}
				});
				cb.cusAgreement1ClickedCallback(new CusAgreement1ClickedCallback() {
					@Override
					public void handle() {
						Log.i(TAG, System.currentTimeMillis() + " cusAgreement1ClickedCallback");
					}
				});
				cb.cusAgreement2ClickedCallback(new CusAgreement2ClickedCallback() {
					@Override
					public void handle() {
						Log.i(TAG, System.currentTimeMillis() + " cusAgreement2ClickedCallback");
					}
				});
				cb.checkboxStatusChangedCallback(new CheckboxStatusChangedCallback() {
					@Override
					public void handle(boolean b) {
						Log.i(TAG,System.currentTimeMillis() + " current status is " + b);
					}
				});
				cb.pageCloseCallback(new PageClosedCallback() {
					@Override
					public void handle() {
						Log.i(TAG, System.currentTimeMillis() + " pageClosed");
						HashMap<String, List<Integer>> map = UiLocationHelper.getInstance().getViewLocations();
						if (map == null) {
							return;
						}
						for (String key : map.keySet()) {
							List<Integer> locats = map.get(key);
							if (locats != null && locats.size() > 0) {
								for (int i : locats) {
									Log.i(TAG, i + " xywh");
								}
							}
						}
					}
				});
			}
		});
		starttime = System.currentTimeMillis();
		SecVerify.verify(new PageCallback() {
			@Override
			public void pageCallback(int code, String desc) {
				Log.e(TAG, code + " " + desc);
				if (code != 6119140) {
					CommonProgressDialog.dismissProgressDialog();
					if (devMode) {
					Toast.makeText(MainActivity.this, code + " " + desc, Toast.LENGTH_SHORT).show();
					}

				}
			}
		}, new GetTokenCallback() {
			@Override
			public void onComplete(VerifyResult data) {
				tokenToPhone(data);
			}

			@Override
			public void onFailure(VerifyException e) {
				showExceptionMsg(e);
			}
		});

	}

	private void tokenToPhone(VerifyResult data) {
		CommonProgressDialog.dismissProgressDialog();
		if (data != null) {
			Log.d(TAG, data.toJSONString());
			// ???????????????????????????token????????????????????????????????????????????????????????????????????????????????????????????????????????????
			CommonProgressDialog.showProgressDialog(MainActivity.this);
			LoginTask.getInstance().login(data, new ResultListener<LoginResult>() {
				@Override
				public void onComplete(LoginResult data) {
					CommonProgressDialog.dismissProgressDialog();
					Log.d(TAG, "Login success. data: " + data.toJSONString());
					vibrate();
					// ???????????????????????????????????????
					goResultActivity(data);
				}

				@Override
				public void onFailure(DemoException e) {
					// ????????????
					Log.e(TAG, "login failed", e);
					CommonProgressDialog.dismissProgressDialog();
					// ?????????
					int errCode = e.getCode();
					// ????????????
					String errMsg = e.getMessage();
					// ??????????????????????????????????????????t?????????????????????t????????????null
					Throwable t = e.getCause();
					String errDetail = null;
					if (t != null) {
						errDetail = t.getMessage();
					}

					String msg = "???????????????????????????????????????????????????" + "\n?????????: " + errCode + "\n????????????: " + errMsg;
					if (!TextUtils.isEmpty(errDetail)) {
						msg += "\n????????????: " + errDetail;
					}
					if (!devMode) {
						msg = "?????????????????????";
					}
					Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
					goResultActivity(null);
					// Demo?????????????????????????????????????????????????????????
					preVerify();
				}
			});
		}
	}

	public void showExceptionMsg(VerifyException e) {
		// ????????????
		if (defaultUi == 1){
			//???????????????????????????????????????????????????????????????
			SecVerify.finishOAuthPage();
		}
		CommonProgressDialog.dismissProgressDialog();
		Log.e(TAG, "verify failed", e);
		// ?????????
		int errCode = e.getCode();
		// ????????????
		String errMsg = e.getMessage();
		// ??????????????????????????????????????????t?????????????????????t????????????null
		Throwable t = e.getCause();
		String errDetail = null;
		if (t != null) {
			errDetail = t.getMessage();
		}

		String msg = "?????????: " + errCode + "\n????????????: " + errMsg;
		if (!TextUtils.isEmpty(errDetail)) {
			msg += "\n????????????: " + errDetail;
		}
		if (!devMode) {
			msg = "?????????????????????";
			if (errCode == VerifyErr.C_NO_SIM.getCode()
					|| errCode == VerifyErr.C_UNSUPPORTED_OPERATOR.getCode()
					|| errCode == VerifyErr.C_CELLULAR_DISABLED.getCode()) {
				msg = errMsg;
			}
		}
		Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
		goResultActivity(null);
	}

	private void customizeUi() {
		SecVerify.setUiSettings(CustomizeUtils.customizeUi());
		SecVerify.setLandUiSettings(CustomizeUtils.customizeLandUi());
	}


	private void customizeUi1() {
		SecVerify.setUiSettings(CustomizeUtils.customizeUi1());
		SecVerify.setLandUiSettings(CustomizeUtils.customizeUi5(this));
//		LandUiSettings uiSettings1  = new LandUiSettings.Builder()
//				.setTranslateAnim(true)
//				.setImmersiveTheme(true)
//				.setImmersiveStatusTextColorBlack(true)
//				.build();
//		SecVerify.setLandUiSettings(uiSettings1);
	}


	private void customizeUi2() {
		SecVerify.setUiSettings(CustomizeUtils.customizeUi2());
		SecVerify.setLandUiSettings(null);
//		LandUiSettings uiSettings1  = new LandUiSettings.Builder()
//				.setDialogMaskBackgroundClickClose(true)
//				.setStartActivityTransitionAnim(R.anim.sec_verify_translate_bottom_in,R.anim.sec_verify_translate_bottom_out)
//				.setFinishActivityTransitionAnim(R.anim.sec_verify_translate_bottom_in,R.anim.sec_verify_translate_bottom_out)
//				.setDialogTheme(true)
//				.setDialogAlignBottom(true)
//				.build();
//		SecVerify.setLandUiSettings(uiSettings1);
	}

	private void customizeUi3() {
		SecVerify.setUiSettings(CustomizeUtils.customizeUi3());
		SecVerify.setLandUiSettings(null);
	}

	private void customizeUi4() {
		SecVerify.setUiSettings(CustomizeUtils.customizeUi4());
		SecVerify.setLandUiSettings(CustomizeUtils.customizeLandUi4());
	}

	/**
	 * ???????????????view
	 */
	private void addCustomView() {
		CustomUIRegister.addCustomizedUi(CustomizeUtils.buildCustomView(MobSDK.getContext()),
				new CustomViewClickCallback(0));

		List<View> views = new ArrayList<View>();
		TextView textView = new TextView(MobSDK.getContext());
		textView.setText("??????");
		textView.setId(R.id.customized_view_id_div);
		textView.setTextColor(0xff000000);
		textView.setTextSize(16);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//		params.addRule(RelativeLayout.RIGHT_OF, R.id.sec_verify_title_bar_left);
		params.addRule(RelativeLayout.CENTER_VERTICAL);
		params.leftMargin = ResHelper.dipToPx(MainActivity.this,35);
		textView.setLayoutParams(params);
		views.add(textView);
		CustomUIRegister.addTitleBarCustomizedUi(views,new CustomViewClickCallback(1));
	}

	private void addCustomView1() {
		CustomUIRegister.addCustomizedUi(CustomizeUtils.buildCustomView(this), new CustomViewClickListener() {
			@Override
			public void onClick(View view) {
				int id = view.getId();
				if (id == R.id.customized_btn_id_1) {
					customizeUi3();
					addCustomView3();
					SecVerify.refreshOAuthPage();
				}
			}
		});
		View view = LayoutInflater.from(this).inflate(R.layout.sec_verify_demo_loading,null);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		view.setLayoutParams(params);
		CustomUIRegister.setCustomizeLoadingView(view);

	}

	private void addCustomView2() {
		CustomUIRegister.addCustomizedUi(CustomizeUtils.buildCustomView2(this), new CustomViewClickListener() {
			@Override
			public void onClick(View view) {
				int id = view.getId();
				String msg = "";
				if (id == R.id.customized_btn_id_1) {
					msg = "??????????????????";
					// ???????????????????????????SecVerify???????????????????????????????????????????????????????????????
					SecVerify.finishOAuthPage();
					CommonProgressDialog.dismissProgressDialog();
				} else if (id == R.id.customized_view_id) {
					return;
				}
				// ???????????????
				Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void addCustomView3() {

		CustomUIRegister.addCustomizedUi(CustomizeUtils.buildCustomView3(this), new CustomViewClickListener() {
			@Override
			public void onClick(View view) {
				int id = view.getId();
				String msg = "";
				if (id == R.id.customized_btn_id_1) {
					msg = "??????1 clicked";
					// ???????????????????????????SecVerify???????????????????????????????????????????????????????????????
					addCustomView1();
					customizeUi2();
					SecVerify.refreshOAuthPage();
				} else if (id == R.id.customized_btn_id_0){
					msg = "???????????? ";
					SecVerify.finishOAuthPage();
				} else if (id == R.id.customized_btn_id_3){
					msg = "????????????";
					SecVerify.finishOAuthPage();
				}
				// ???????????????
				CommonProgressDialog.dismissProgressDialog();
				Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void addCustomView4() {
		CustomUIRegister.addCustomizedUi(CustomizeUtils.buildCustomView4(MobSDK.getContext()),
				null);
		CustomUIRegister.addTitleBarCustomizedUi(null,null);
	}

	private void goResultActivity(LoginResult data) {
		Intent i = new Intent(this, ResultActivity.class);
		if (data != null) {
			i.putExtra("sec_verify_demo_verify_success", true);
			i.putExtra(Const.EXTRAS_DEMO_LOGIN_RESULT, data);
		} else {
			i.putExtra("sec_verify_demo_verify_success", false);
		}
		startActivityForResult(i, REQUEST_CODE);
		SecVerify.finishOAuthPage();
		com.mob.secverify.ui.component.CommonProgressDialog.dismissProgressDialog();
	}

	private void vibrate() {
		Vibrator vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
		if (vibrator != null) {
			if (Build.VERSION.SDK_INT >= 26) {
				VibrationEffect vibrationEffect = VibrationEffect.createOneShot(500, 20);
				vibrator.vibrate(vibrationEffect);
			} else {
				vibrator.vibrate(500);
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (defaultUi == 1 || defaultUi == 3) {
			//???????????????????????????????????????Activity????????????????????????????????????????????????????????????
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//???????????????????????????????????????
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (defaultUi == 1 || defaultUi == 3) {
			//???????????????????????????????????????Activity????????????????????????????????????????????????????????????
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
		}
	}


	private void switchDevMode() {
		if (devMode) {
			devMode = false;
			Toast.makeText(this, "??????????????????Off", Toast.LENGTH_SHORT).show();
		} else {
			devMode = true;
			Toast.makeText(this, "??????????????????On", Toast.LENGTH_SHORT).show();
		}
	}

	private static class CustomViewClickCallback implements CustomViewClickListener {
		private int customUI;

		public CustomViewClickCallback(int customUI) {
			this.customUI = customUI;
		}

		@Override
		public void onClick(View view) {
			if (customUI == 0) {
				int id = view.getId();
				String msg = "";
				if (id == R.id.customized_btn_id_1) {
					msg = "?????? clicked";
					// ???????????????????????????SecVerify???????????????????????????????????????????????????????????????
					SecVerify.finishOAuthPage();
					Toast.makeText(MobSDK.getContext(), msg, Toast.LENGTH_SHORT).show();
				} else if (id == R.id.customized_btn_id_3) {
//					msg = "??????2 clicked";
					Intent intent = new Intent(MobSDK.getContext(), DemoLoginActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					MobSDK.getContext().startActivity(intent);
				}
				// ???????????????
			} else if (customUI == 1) {
				// ???????????????
				SecVerify.finishOAuthPage();
			} else if (customUI == 4){
				int id = view.getId();
				String msg = "";
				if (id == R.id.customized_btn_id_1) {
					// ???????????????????????????SecVerify???????????????????????????????????????????????????????????????
				} else if (id == R.id.customized_view_id) {
					return;
				}
				// ???????????????
			}
			CommonProgressDialog.dismissProgressDialog();
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		onCreate(null);
	}

	private void showMobPrivacy() {

		sharePrefrenceHelper = new SharePrefrenceHelper(MobSDK.getContext());
		sharePrefrenceHelper.open("privacy", 1);

		if (sharePrefrenceHelper.getBoolean("isGrant")) {
			return;
		}

		MobSDK.getPrivacyPolicyAsync(MobSDK.POLICY_TYPE_URL, new PrivacyPolicy.OnPolicyListener() {
			@Override
			public void onComplete(PrivacyPolicy data) {
				if (data != null) {
					// ???????????????
					final String url = data.getContent();
					String content = MainActivity.this.getResources().getString(R.string.sec_verify_demo_privacy_text);
					SpannableString spanStr = new SpannableString(content);
//					spanStr.setSpan(new ForegroundColorSpan(baseColor), 0, agreement.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
					String mobService = MainActivity.this.getResources().getString(R.string.sec_verify_demo_privacy_title);
					int privacyIndex = content.indexOf(mobService);
					spanStr.setSpan(new ForegroundColorSpan(Color.parseColor("#FE7A4E")), privacyIndex, privacyIndex + mobService.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
					spanStr.setSpan(new ClickableSpan() {
						@Override
						public void updateDrawState(TextPaint ds) {
							ds.setUnderlineText(false);
						}

						@Override
						public void onClick(View widget) {
							AgreementPage page = new AgreementPage();
							Intent i = new Intent();
							i.putExtra("extra_agreement_url", url);
							page.show(MainActivity.this, i);
						}
					}, privacyIndex, privacyIndex + mobService.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


					PrivacyDialog.showPrivacyDialog(MainActivity.this, spanStr, new ResultListener() {
						@Override
						public void onComplete(Object data) {
							submitPolicyGrantResult(true);
							sharePrefrenceHelper.putBoolean("isGrant", true);
						}

						@Override
						public void onFailure(DemoException e) {
							submitPolicyGrantResult(false);
							sharePrefrenceHelper.putBoolean("isGrant", true);
						}
					});

				}
			}

			@Override
			public void onFailure(Throwable t) {
				Log.d(TAG, "????????????????????????????????????");
			}
		});
	}
}
