package com.sc.reminder.utils;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.text.TextUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sc.reminder.R;

public class PlayStone {

	private static PlayStone playStone = null;
	private MediaPlayer player;
	public String path;
	public RelativeLayout mover;
	public ImageView iv_view_player;
	public ImageView iv_view_player_point;
	
	private PlayStone() {
		player = new MediaPlayer();
	}

	public static PlayStone getInstance() {
		if (playStone == null) {
			playStone = new PlayStone();
		}
		return playStone;
	}
	
	public void check() {
		if (null == player) {
			player = new MediaPlayer();
		}
	}
	
	public void play(String path, final RelativeLayout mover) {
		check();
		if (null != player) {
			if (player.isPlaying()) {
				stop();
//				if (mover != this.mover) {
//					start(path, mover);
//				}
			}
			else {
				start(path, mover);
			}
		}
	}
	
	public void start(String path, final RelativeLayout mover) {
		this.path = path;
		this.mover = mover;
		final TextView iv_view_player_time = (TextView) mover.findViewById(R.id.iv_view_player_time);
		final ImageView iv_view_player = (ImageView) mover.findViewById(R.id.iv_view_player);
		final ImageView iv_view_player_point = (ImageView) mover.findViewById(R.id.iv_view_player_point);
		final RelativeLayout rl_view_player_mover = (RelativeLayout) mover.findViewById(R.id.rl_view_player_mover);
		this.iv_view_player = iv_view_player;
		this.iv_view_player_point = iv_view_player_point;
		try {
			if (null != player) {
				if (!TextUtils.isEmpty(path)) {
					player.setOnCompletionListener(new OnCompletionListener() {
						
						@Override
						public void onCompletion(MediaPlayer mp) {
							stop();
//							Drawable background = iv_rolling.getBackground();
//							if (background instanceof AnimationDrawable) {
//								AnimationDrawable animation = (AnimationDrawable) background;
//								animation.stop();
//								animation.selectDrawable(0);
//							}
						}
					});
					player.reset();
					player.setDataSource(path);
				    player.prepare();
				    player.start();	
				    
					iv_view_player.setBackgroundResource(R.drawable.play3);
					TranslateAnimation translateAnimation = new TranslateAnimation(0, rl_view_player_mover.getMeasuredWidth() - iv_view_player_point.getMeasuredWidth(), 0, 0);
					translateAnimation.setDuration(((Integer) iv_view_player_time.getTag() * 1200));//由于启动延迟，动画多加20%时间
					translateAnimation.setFillAfter(true);
					iv_view_player_point.startAnimation(translateAnimation);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void stop() {
		if (null != player) {
			if (player.isPlaying()) {
				player.stop();
			}
			if (null != mover) {
				if (null != iv_view_player) {
					iv_view_player.setBackgroundResource(R.drawable.play1);
				}
				if (null != iv_view_player_point) {
					iv_view_player_point.clearAnimation();
				}
			}
		}
	}
	
	public void release() {
		if (null != player) {
			if (player.isPlaying()) {
				stop();
			}
			player.release();
			player = null;
		}		
	}
}
