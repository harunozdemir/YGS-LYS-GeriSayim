package com.time.ygslys_gerisayim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {
	long systemMs, ygsMs, lysMs, gecici;
	long ygsGun, ygsSaat, ygsDakika, ygsSaniye, ygsSaniyeBolum, ygsSaatBolum; // ygs
																				// variable
	long lysGun, lysSaat, lysDakika, lysSaniye, lysSaniyeBolum; // lys variable
	int yilYgs = 2016, yilLys = 2016;
	Handler handle = null;
	Runnable runnable = null;
	TextView tvYgsGun, tvYgsSt, tvYgsDk, tvYgsSn, tvLysGun, tvLysSt, tvLysDk,
			tvLysSn, tvTarihYgs, tvTarihLys;

	private String ygsAppLink = "https://play.google.com/store/apps/details?id=com.time.ygslys_gerisayim";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// ygs tv
		tvYgsGun = (TextView) findViewById(R.id.tvGun);
		tvYgsSt = (TextView) findViewById(R.id.tvSaat);
		tvYgsDk = (TextView) findViewById(R.id.tvDakika);
		tvYgsSn = (TextView) findViewById(R.id.tvSaniye);
		tvTarihYgs = (TextView) findViewById(R.id.tvTarihYgs);

		// lys tv
		tvLysGun = (TextView) findViewById(R.id.tvLysGun);
		tvLysSt = (TextView) findViewById(R.id.tvLysSaat);
		tvLysDk = (TextView) findViewById(R.id.tvLysDakika);
		tvLysSn = (TextView) findViewById(R.id.tvLysSaniye);
		tvTarihLys = (TextView) findViewById(R.id.tvTarihLys);

		// times
		ygsMs = 1457906400000L;
		lysMs = 1466373600000L;

		handle = new Handler();

		// timer
		runnable = new Runnable() {
			@Override
			public void run() {
				if (System.currentTimeMillis() >= ygsMs) {
					ygsMs += 31536000000L;
					yilYgs++;
				}
				if (System.currentTimeMillis() >= lysMs) {
					lysMs += 31536000000L;
					yilLys++;
				}
				ygsZaman();
				lysZaman();

				handle.postDelayed(runnable, 0);
			}
		};

		runnable.run();

	}

	public void ygsZaman() {
		systemMs = System.currentTimeMillis();

		gecici = (ygsMs - systemMs) / 1000;
		ygsSaniye = gecici % 60;
		ygsSaniyeBolum = gecici / 60;
		ygsDakika = ygsSaniyeBolum % 60;
		ygsSaat = ygsSaniyeBolum / 60;
		ygsGun = ygsSaat;
		ygsSaat %= 24;
		ygsGun /= 24;

		tvYgsGun.setText((int) ygsGun + "  gün ");
		tvYgsSt.setText((int) ygsSaat + "  saat ");
		tvYgsDk.setText((int) ygsDakika + " dakika ");
		tvYgsSn.setText((int) ygsSaniye + " saniye kaldı ");
		tvTarihYgs.setText("Tarih: 13 Mart " + yilYgs
				+ " - Pazar --  Saat: 10:00");

	}

	public void lysZaman() {
		systemMs = System.currentTimeMillis();

		gecici = (lysMs - systemMs) / 1000;
		lysSaniye = gecici % 60;
		lysSaniyeBolum = gecici / 60;
		lysDakika = lysSaniyeBolum % 60;
		lysSaat = lysSaniyeBolum / 60;
		lysGun = lysSaat;
		lysSaat %= 24;
		lysGun /= 24;

		tvLysGun.setText((int) lysGun + "  gün ");
		tvLysSt.setText((int) lysSaat + "  saat ");
		tvLysDk.setText((int) lysDakika + " dakika ");
		tvLysSn.setText((int) lysSaniye + " saniye kaldı ");
		tvTarihLys.setText("Tarih: 19 - 26 Haziran " + yilLys
				+ " -- Saat: 10:00");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.share, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.shareApp:
			share();

			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void share() {

		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(Intent.EXTRA_TEXT, ygsAppLink);
		startActivity(Intent.createChooser(shareIntent,
				getString(R.string.paylas)));

	}

}
