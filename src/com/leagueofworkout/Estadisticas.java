package com.leagueofworkout;

import java.io.IOException;
import java.util.StringTokenizer;

import com.leagueofworkout.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class Estadisticas extends Activity {

	private TextView a, b, c, d, e, f, g, h, i;
	private String v1, v2, v3, v4, v5, v6, v7, v8, v9;
	private ProgressDialog pd;
	private ImageView imagen;
	private AdaptadorBD bd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_estadisticas);
		imagen = (ImageView) findViewById(R.id.elo);

		// con la suma total de puntos muestra los elos
		try {
			if (this.puntuacionesTotales() < 300) {
				imagen.setImageResource(R.drawable.bronce);
			} else if (this.puntuacionesTotales() >= 300
					&& this.puntuacionesTotales() < 700) {
				imagen.setImageResource(R.drawable.plata);
			} else if (this.puntuacionesTotales() >= 700
					&& this.puntuacionesTotales() < 1200) {
				imagen.setImageResource(R.drawable.gold);
			} else if (this.puntuacionesTotales() >= 1200
					&& this.puntuacionesTotales() < 1800) {
				imagen.setImageResource(R.drawable.plat);
			} else if (this.puntuacionesTotales() >= 1800
					&& this.puntuacionesTotales() < 2700) {
				imagen.setImageResource(R.drawable.diamon);
			} else if (this.puntuacionesTotales() >= 2700
					&& this.puntuacionesTotales() < 4500) {
				imagen.setImageResource(R.drawable.master);
			} else {
				imagen.setImageResource(R.drawable.chall);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		a = (TextView) findViewById(R.id.rodillas);
		b = (TextView) findViewById(R.id.flexiones);
		c = (TextView) findViewById(R.id.escalador);
		d = (TextView) findViewById(R.id.levantamientopiernas);
		e = (TextView) findViewById(R.id.sentadillas);
		f = (TextView) findViewById(R.id.saltos);
		g = (TextView) findViewById(R.id.zancadas);
		h = (TextView) findViewById(R.id.abdominales);
		i = (TextView) findViewById(R.id.flexionesunapierna);

		this.leerPuntuaciones();
		Log.e("Contenido fichero Estadisticas", this.leerSQL());
		a.setText(v1);
		b.setText(v2);
		c.setText(v3);
		d.setText(v4);
		e.setText(v5);
		f.setText(v6);
		g.setText(v7);
		h.setText(v8);
		i.setText(v9);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.estadisticas, menu);
		return true;
	}

	protected void onDestroy() {
		if (pd != null) {
			pd.dismiss();
		}
		super.onDestroy();
	}

	private String leerSQL() {
		String x;
		bd = new AdaptadorBD(this);
		bd.open();
		// Log.d("BASE DE DATOS QUE DEBERIA FUNCIONAR", bd.get(1).getString(1));
		x = bd.get(1).getString(1);
		bd.close();
		return x;
	}

	public void leerPuntuaciones() {
		String datos = this.leerSQL();
		StringTokenizer st = new StringTokenizer(datos, "+");
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (token.contains("a")) {
				String num = token.substring(1);
				v1 = String.valueOf((Integer.parseInt(num)));
			} else if (token.contains("b")) {
				String num = token.substring(1);
				v2 = String.valueOf((Integer.parseInt(num)));
			} else if (token.contains("c")) {
				String num = token.substring(1);
				v3 = String.valueOf((Integer.parseInt(num)));
			} else if (token.contains("d")) {
				String num = token.substring(1);
				v4 = String.valueOf((Integer.parseInt(num)));
			} else if (token.contains("e")) {
				String num = token.substring(1);
				v5 = String.valueOf((Integer.parseInt(num)));
			} else if (token.contains("f")) {
				String num = token.substring(1);
				v6 = String.valueOf((Integer.parseInt(num)));
			} else if (token.contains("g")) {
				String num = token.substring(1);
				v7 = String.valueOf((Integer.parseInt(num)));
			} else if (token.contains("h")) {
				String num = token.substring(1);
				v8 = String.valueOf((Integer.parseInt(num)));
			} else if (token.contains("i")) {
				String num = token.substring(1);
				v9 = String.valueOf((Integer.parseInt(num)));
			}
		}
	}

	public int puntuacionesTotales() throws IOException {
		String datos = this.leerSQL();
		int punt = 0;

		StringTokenizer st = new StringTokenizer(datos, "+");
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			String num = token.substring(1);
			punt += Integer.parseInt(num);
		}
		return punt;
	}

}
