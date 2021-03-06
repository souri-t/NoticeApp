package com.ametice.noticenotice;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by Masato on 2015/09/26.
 * 送信設定画面の動作
 * 最終更新 :2015/11/21 y.hiyoshi
 * @version 1.2
 */
public class SendSettingActivity extends Activity {

    Context context;
    UserNoticeSetting uns;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        uns = new UserNoticeSetting(context);

        setContentView(R.layout.send_setting);

        /*   曜日指定設定ボタン   */
        Button dayOfWeekBtn = (Button)findViewById(R.id.btn_dayofweek);
        dayOfWeekBtn.setOnClickListener(new SetingBtnClickListener());

        /*   確認開始時刻設定ボタン   */
        Button startTimeBtn = (Button)findViewById(R.id.btn_starttime);
        startTimeBtn.setOnClickListener(new SetingBtnClickListener());

        /*   確認終了時刻設定ボタン   */
        Button endTimeBtn = (Button)findViewById(R.id.btn_endtime);
        endTimeBtn.setOnClickListener(new SetingBtnClickListener());

        /*   確認間隔設定ボタン   */
        Button intervalBtn = (Button)findViewById(R.id.btn_interval);
        intervalBtn.setOnClickListener(new SetingBtnClickListener());

        /*   設定完了ボタン   */
        Button sendSettingBtn = (Button)findViewById(R.id.btn_sendsetting);
        sendSettingBtn.setOnClickListener(new SetingBtnClickListener());

        /*  設定情報表示領域のハンドル生成    */
        TextView dayOfWeekMsg = (TextView)findViewById(R.id.text_dayofweekmsg);
        dayOfWeekMsg.setText(getDayOfWeekText());

        /*  設定情報表示領域のハンドル生成    */
        TextView startTimeMsg = (TextView)findViewById(R.id.text_starttimemsg);
        startTimeMsg.setText(toTimeFormat(new NoticeSaveData(context).loadStartTime()));

        /*  設定情報表示領域のハンドル生成    */
        TextView endTimeMsg = (TextView)findViewById(R.id.text_endtimemsg);
        endTimeMsg.setText(toTimeFormat(new NoticeSaveData(context).loadEndTime()));

        /*  設定情報表示領域のハンドル生成    */
        TextView intervalMsg = (TextView)findViewById(R.id.text_intervalmsg);
        intervalMsg.setText(toIntervalFormat(new NoticeSaveData(context).loadCheckInterval()));

    }

    /*      ボタンイベントリスナー  */
    private class SetingBtnClickListener implements View.OnClickListener {
        public void onClick(View v) {
            TextView tv;
            switch (v.getId()) {
                case R.id.btn_dayofweek:
                    /*  設定情報表示領域のハンドル生成    */
                    tv = (TextView)findViewById(R.id.text_dayofweekmsg);

                    /*  ダイアログ生成 */
                    uns.onDayOfWeekSetting(tv);
                    break;

                case R.id.btn_starttime:
                    /*  設定情報表示領域のハンドル生成    */
                    tv = (TextView)findViewById(R.id.text_starttimemsg);

                    /*  ダイアログ生成 */
                    uns.onStartTimeSetting(tv);
                    break;

                case R.id.btn_endtime:
                    /*  設定情報表示領域のハンドル生成    */
                    tv = (TextView)findViewById(R.id.text_endtimemsg);

                    /*  ダイアログ生成 */
                    uns.onEndTimeSetting(tv);

                    break;

                case R.id.btn_interval:
                    /*  設定情報表示領域のハンドル生成    */
                    tv = (TextView)findViewById(R.id.text_intervalmsg);

                    /*  ダイアログ生成 */
                    uns.onIntervalSetting(tv);

                    break;
                case R.id.btn_sendsetting:
                    /*   端末内部へ設定値の一括保存   */
                    uns.saveTimeData();

                    /*   アクティビティを破棄し遷移前画面に戻る   */
                    finish();

                    break;

                default:
                    /*  DO NOTHING  */
                    break;
            }
        }
    }

    /**
     * 設定された曜日の文字列取得メソッド
     */
    public String getDayOfWeekText(){

        int cnt = 0;

        /*  曜日チェックの状態    */
        boolean dayOfWeekChecks[] = new boolean[7];
        String allDayOfWeek;
        StringBuilder strb = new StringBuilder();

        /*  曜日リスト    */
        HashMap<Integer,String> dayOfWeekList = new HashMap<Integer,String>();

        /*  曜日リストの生成    */
        dayOfWeekList.put(1, "日");
        dayOfWeekList.put(2, "月");
        dayOfWeekList.put(3, "火");
        dayOfWeekList.put(4, "水");
        dayOfWeekList.put(5, "木");
        dayOfWeekList.put(6, "金");
        dayOfWeekList.put(7, "土");

        /*  前回値を表示する（日〜土[1-7]）    */
        for(int i = 1; i <= 7; i++){
            if(new NoticeSaveData(context).loadDayOfWeek(i) == true){
                strb.append(dayOfWeekList.get(i));
                cnt++;
            }
        }

        allDayOfWeek = strb.toString();

        if(cnt == 0){
            allDayOfWeek = "none";
        }

        return allDayOfWeek;
    }

    /**
     * 表示用時刻フォーマット変換
     * @param time 時刻文字列
     * @return 表示用時刻文字列
     */
    private String toTimeFormat(String time){
        int hour;
        int minute;
        try {
             /*  文字列分割   */
            String splitTime[] = time.split(":", 2);
            hour = Integer.parseInt(splitTime[0]);
            minute = Integer.parseInt(splitTime[1]);
        }catch (Exception e){
            hour = 0;
            minute = 0;
        }

        /*  2ケタの時刻文字列を生成   */
        return String.format("%1$02d", hour)+ ":" + String.format("%1$02d", minute);
    }

    /**
     * 確認間隔フォーマット変換
     * @param interval 確認間隔
     * @return 表示用確認間隔
     */
    private String toIntervalFormat(String interval){

        String intervalText;

        if (interval.equals("0") == true || interval.equals("") == true){
            intervalText = "none";
        }else{
            intervalText = interval + "分";
        }

        return intervalText;
    }
 }
