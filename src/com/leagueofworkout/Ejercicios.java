package com.leagueofworkout;

import java.io.IOException;
import java.util.StringTokenizer;
import com.leagueofworkout.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Ejercicios extends Activity {

	private ImageView imagen;
	private TextView repView, tipo_ejer;
	private String[] info;
	private int rutina, ejerciciosTotal, estado;
	private boolean fin = false;
	private float numRep;
	private AdaptadorBD bd;

	// al principio lee la cadena y depende de lo recibido ejecuta por primera
	// vez el ejercicio, luego pasa al procedimiento de abajo
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ejercicios);

		Bundle b = this.getIntent().getExtras();
		info = b.getStringArray("ejer");

		imagen = (ImageView) findViewById(R.id.imageView1);
		repView = (TextView) findViewById(R.id.numveces);
		tipo_ejer = (TextView) findViewById(R.id.Tipo_Ej);

		numRep = ((float) Integer.parseInt(info[0].toString())) / 2;
		rutina = Integer.parseInt(info[1].toString());
		if (rutina == 1) {
			imagen.setImageResource(R.drawable.ej8);
			ejerciciosTotal = (int) (numRep * 12);

			repView.setText(String.valueOf(ejerciciosTotal));
			tipo_ejer.setText(R.string.ej8);
			try {
				puntuaciones("h", ejerciciosTotal);
			} catch (IOException e) {
				e.printStackTrace();
			}
			estado = 1;
		} else if (rutina == 2) {
			imagen.setImageResource(R.drawable.ej8);
			ejerciciosTotal = (int) (numRep * 12);
			repView.setText(String.valueOf(ejerciciosTotal));
			tipo_ejer.setText(R.string.ej8);
			try {
				puntuaciones("b", ejerciciosTotal);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			estado = 1;
		} else {
			imagen.setImageResource(R.drawable.ej5);
			ejerciciosTotal = (int) (numRep * 8);
			repView.setText(String.valueOf(ejerciciosTotal));
			tipo_ejer.setText(R.string.ej5);
			try {
				puntuaciones("e", ejerciciosTotal);
			} catch (IOException e) {
				e.printStackTrace();
			}
			estado = 1;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.ejercicios, menu);
		return true;
	}

	// crea la lista de ejercicios y la termina volviendo al main.
	@SuppressLint("NewApi")
	public void sig(View v) throws IOException {
		if (fin) {
			Intent act = new Intent(this, MainActivity.class);
			startActivity(act);
			finish();
		} else {
			if (rutina == 1) {
				if (estado == 1) {
					imagen.setImageResource(R.drawable.ej7);
					ejerciciosTotal = (int) (numRep * 10);
					repView.setText(String.valueOf(ejerciciosTotal));
					tipo_ejer.setText(R.string.ej7);
					puntuaciones("g", ejerciciosTotal);
					estado++;
				} else if (estado == 2) {
					imagen.setImageResource(R.drawable.ej6);
					ejerciciosTotal = (int) (numRep * 10);
					repView.setText(String.valueOf(ejerciciosTotal));
					tipo_ejer.setText(R.string.ej6);
					puntuaciones("f", ejerciciosTotal);
					estado++;
				} else if (estado == 3) {
					imagen.setImageResource(R.drawable.ej1);
					ejerciciosTotal = (int) (numRep * 12);
					repView.setText(String.valueOf(ejerciciosTotal));
					tipo_ejer.setText(R.string.ej1);
					puntuaciones("a", ejerciciosTotal);
					estado++;
				} else if (estado == 4) {
					imagen.setImageResource(R.drawable.ej9);
					ejerciciosTotal = (int) (numRep * 8);
					repView.setText(String.valueOf(ejerciciosTotal));
					tipo_ejer.setText(R.string.ej9);
					puntuaciones("i", ejerciciosTotal);
					fin = true;
				}
			}
			if (rutina == 2) {
				if (estado == 1) {
					imagen.setImageResource(R.drawable.ej3);
					ejerciciosTotal = (int) (numRep * 12);
					repView.setText(String.valueOf(ejerciciosTotal));
					tipo_ejer.setText(R.string.ej3);
					puntuaciones("c", ejerciciosTotal);
					estado++;
				} else if (estado == 2) {
					imagen.setImageResource(R.drawable.ej1);
					ejerciciosTotal = (int) (numRep * 12);
					repView.setText(String.valueOf(ejerciciosTotal));
					tipo_ejer.setText(R.string.ej1);
					puntuaciones("a", ejerciciosTotal);
					estado++;
				} else if (estado == 3) {
					imagen.setImageResource(R.drawable.ej4);
					ejerciciosTotal = (int) (numRep * 8);
					repView.setText(String.valueOf(ejerciciosTotal));
					tipo_ejer.setText(R.string.ej4);
					puntuaciones("d", ejerciciosTotal);
					estado++;
				} else if (estado == 4) {
					imagen.setImageResource(R.drawable.ej2);
					ejerciciosTotal = (int) (numRep * 8);
					repView.setText(String.valueOf(ejerciciosTotal));
					tipo_ejer.setText(R.string.ej2);
					puntuaciones("b", ejerciciosTotal);
					fin = true;
				}
			}
			if (rutina == 3) {
				if (estado == 1) {
					imagen.setImageResource(R.drawable.ej3);
					ejerciciosTotal = (int) (numRep * 12);
					repView.setText(String.valueOf(ejerciciosTotal));
					tipo_ejer.setText(R.string.ej3);
					puntuaciones("c", ejerciciosTotal);
					estado++;
				} else if (estado == 2) {
					imagen.setImageResource(R.drawable.ej4);
					ejerciciosTotal = (int) (numRep * 8);
					repView.setText(String.valueOf(ejerciciosTotal));
					tipo_ejer.setText(R.string.ej4);
					puntuaciones("d", ejerciciosTotal);
					estado++;
				} else if (estado == 3) {
					imagen.setImageResource(R.drawable.ej6);
					ejerciciosTotal = (int) (numRep * 10);
					repView.setText(String.valueOf(ejerciciosTotal));
					tipo_ejer.setText(R.string.ej6);
					puntuaciones("f", ejerciciosTotal);
					estado++;
				} else if (estado == 4) {
					imagen.setImageResource(R.drawable.ej2);
					ejerciciosTotal = (int) (numRep * 8);
					repView.setText(String.valueOf(ejerciciosTotal));
					tipo_ejer.setText(R.string.ej2);
					puntuaciones("b", ejerciciosTotal);
					fin = true;
				}
			}
		}
	}

	public void EscribeFicheroSQL(String text) {
		bd = new AdaptadorBD(this);
		bd.open();
		Log.d("BASE DE DATOS QUE DEBERIA FUNCIONAR", bd.get(1).getString(1));
		bd.actualizar(1, text);
		bd.close();
	}

	private String leerInputSQL() {
		String x;
		bd = new AdaptadorBD(this);
		bd.open();
		Log.d("BASE DE DATOS QUE DEBERIA FUNCIONAR", bd.get(1).getString(1));
		x = bd.get(1).getString(1);
		bd.close();
		return x;
	}

	// va añadiendo los valores en la base de dato(modifica la 1º columna solo)
	public void puntuaciones(String tipoEjercicio, int repeticionesHechas)
			throws IOException {
		String datos = this.leerInputSQL();
		Log.e("Lo que lee el ejercicios", datos);

		StringTokenizer st = new StringTokenizer(datos, "+");
		StringBuilder sb = new StringBuilder();
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (token.contains(tipoEjercicio)) {
				String num = token.substring(1);
				token = tipoEjercicio
						+ String.valueOf((Integer.parseInt(num) + repeticionesHechas));
			}

			sb.append("+" + token);
		}
		this.EscribeFicheroSQL(sb.toString());
	}

}
