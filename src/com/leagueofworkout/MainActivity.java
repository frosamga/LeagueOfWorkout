package com.leagueofworkout;

import java.util.ArrayList;
import java.util.StringTokenizer;

import main.java.riotapi.RiotApi;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//FUN ELODIN, MIKHAYLO21
public class MainActivity extends Activity {
	private int Kills, Deaths, Assistances;
	private EditText k, d, a;
	private Object[] cadena;
	final static String ACT_INFO1 = "com.example.leagueofworkout.Ejercicios";
	final static String ACT_INFO2 = "com.example.leagueofworkout.Estadisticas";
	private Context context;
	private ProgressDialog pd;
	private RadioGroup rg;
	private RadioButton rb;
	private TextView textoMuestraKDA;
	private EditText nombreInv;
	private Spinner spinner;
	private AdaptadorBD bd;
	private String key = "PRIVATE KEY";
	private RiotApi api;
	private static final int MENU_OPC1 = 1;
	private static final int MENU_OPC2 = 2;
	private boolean usarMetodoOnline = false;
	private ArrayList<String> lista;
	private String response = "";
	private String idInv = "";
	protected int total;
	protected String region = "EUW";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textoMuestraKDA = (TextView) findViewById(R.id.textViewKDA);
		nombreInv = (EditText) findViewById(R.id.nombreInvocador);
		spinner = (Spinner) findViewById(R.id.spinner1);
		DatosPorDefecto();

		rg = (RadioGroup) findViewById(R.id.radiogr1);
		k = (EditText) findViewById(R.id.kills);
		d = (EditText) findViewById(R.id.death);
		a = (EditText) findViewById(R.id.assi);

		// Cambio de GUI
		textoMuestraKDA.setVisibility(View.INVISIBLE);
		k.setVisibility(View.INVISIBLE);
		d.setVisibility(View.INVISIBLE);
		a.setVisibility(View.INVISIBLE);

		cadena = new String[2];
		this.crearSQL();
		context = this;
	}

	private void DatosPorDefecto() {
		lista = new ArrayList<String>();
		lista.add("NA");
		lista.add("EUW");
		lista.add("EUNE");
		lista.add("BR");
		lista.add("TR");
		lista.add("RU");
		lista.add("LAN");
		lista.add("LAS");
		lista.add("OCE");
		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, lista);
		adaptador
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adaptador);
		SpinnerLista sp = new SpinnerLista();
		spinner.setOnItemSelectedListener(sp);
		region = sp.getSpinnerName();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		if (usarMetodoOnline) {
			menu.add(Menu.NONE, MENU_OPC1, Menu.NONE, "On-line").setIcon(
					android.R.drawable.btn_star_big_on);
			menu.add(Menu.NONE, MENU_OPC2, Menu.NONE, "Off-Line").setIcon(
					android.R.drawable.btn_star_big_off);
		} else {
			menu.add(Menu.NONE, MENU_OPC1, Menu.NONE, "On-line").setIcon(
					android.R.drawable.btn_star_big_off);
			menu.add(Menu.NONE, MENU_OPC2, Menu.NONE, "Off-Line").setIcon(
					android.R.drawable.btn_star_big_on);
		}
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_OPC1:
			usarMetodoOnline = true;
			textoMuestraKDA.setVisibility(View.INVISIBLE);
			k.setVisibility(View.INVISIBLE);
			d.setVisibility(View.INVISIBLE);
			a.setVisibility(View.INVISIBLE);
			nombreInv.setVisibility(View.VISIBLE);
			spinner.setVisibility(View.VISIBLE);
			return true;
		case MENU_OPC2:
			usarMetodoOnline = false;
			textoMuestraKDA.setVisibility(View.VISIBLE);
			k.setVisibility(View.VISIBLE);
			d.setVisibility(View.VISIBLE);
			a.setVisibility(View.VISIBLE);
			nombreInv.setVisibility(View.INVISIBLE);
			spinner.setVisibility(View.INVISIBLE);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	protected void onDestroy() {
		if (pd != null) {
			pd.dismiss();
		}
		super.onDestroy();
	}

	@SuppressLint("NewApi")
	public void go(View v) {

		if (usarMetodoOnline) {
			int totalRepeticiones;
			if (nombreInv.getText().toString().isEmpty()) {
				Toast toast = Toast.makeText(this, R.string.faltanombre,
						Toast.LENGTH_SHORT);
				toast.show();
			} else {
				// api = new RiotApi(key);
				new Thread(new Runnable() {
					private String responseID;

					public void run() {
						String nombre = nombreInv.getText().toString()
								.replaceAll("\\s", "");
						String version = "v1.4";
						String version1 = "v1.3";
						String idURL = "https://" + region
								+ ".api.pvp.net/api/lol/" + region + "/"
								+ version + "/summoner/by-name/" + nombre
								+ "?api_key=" + key;
						HttpClient client = new DefaultHttpClient();
						HttpGet get = new HttpGet(idURL);
						HttpResponse responseGet = null;
						try {
							responseGet = client.execute(get);
							HttpEntity resEntityGet = responseGet.getEntity();
							responseID = EntityUtils.toString(resEntityGet);
						} catch (Exception e) {
							e.printStackTrace();
						}
						idInv = this.conseguirID(responseID);
						// Si no lo encuentra da -1
						if (idInv != "-1") {
							String urlInvocador = "https://" + region
									+ ".api.pvp.net/api/lol/" + region + "/"
									+ version1 + "/game/by-summoner/" + idInv
									+ "/recent?api_key=" + key;
							HttpGet get1 = new HttpGet(urlInvocador);
							HttpResponse responseGet1 = null;
							try {
								responseGet1 = client.execute(get1);
								HttpEntity resEntityGet1 = responseGet1
										.getEntity();
								response = EntityUtils.toString(resEntityGet1);
							} catch (Exception e) {
								e.printStackTrace();
							}
							ObtenerKDA(response);
						}
						total = getDeaths() - (getKills() / 3)
								- (getAssistances() / 7);
					}

					private String conseguirID(String json) {
						if (json.contains("error")
								|| json.contains("not found")
								|| json.contains("404")) {
							return "-1";
						} else {
							StringTokenizer st = new StringTokenizer(json, ",");
							StringTokenizer st1 = new StringTokenizer(st
									.nextToken(), ":");
							st1.nextToken();
							st1.nextToken();
							return st1.nextToken();
						}
					}
				}).start();
				SystemClock.sleep(2000);
				if (total < 3) {
					Toast toast = Toast.makeText(this, R.string.op1,
							Toast.LENGTH_SHORT);
					toast.show();
					totalRepeticiones = 1;
				} else if (total > 3 && total <= 6) {
					Toast toast = Toast.makeText(this, R.string.op2,
							Toast.LENGTH_SHORT);
					toast.show();
					totalRepeticiones = 2;
				} else if (total > 6 && total <= 8) {
					Toast toast = Toast.makeText(this, R.string.op3,
							Toast.LENGTH_SHORT);
					toast.show();
					totalRepeticiones = 3;
				} else {
					Toast toast = Toast.makeText(this, R.string.op4,
							Toast.LENGTH_SHORT);
					toast.show();
					totalRepeticiones = 4;
				}
				// comprobacion mediante texto para saber que opcion del
				// textButton
				// se ha cogido
				int tipoEjercicio = rg.getCheckedRadioButtonId();
				rb = (RadioButton) findViewById(tipoEjercicio);
				// lo pasa por el segundo parametro de la cadena
				if (rb.getText().equals("Entrenamiento blando")
						|| rb.getText().equals("Soft training")
						|| rb.getText().equals("Allenamento facile")) {
					cadena[1] = String.valueOf(1);
				} else if (rb.getText().equals("Entrenamiento medio")
						|| rb.getText().equals("Average training")
						|| rb.getText().equals("Allenamento mezzo")) {
					cadena[1] = String.valueOf(2);
				} else {
					cadena[1] = String.valueOf(3);
				}
				cadena[0] = String.valueOf(totalRepeticiones);
				Intent act = new Intent(this, Ejercicios.class);
				act.putExtra("ejer", cadena);
				startActivity(act);
				finish();
			}

		} else {
			// Comprobacion de que el KDA no este vacio
			int totalRepeticiones;
			if (k.getText().toString().isEmpty()
					|| d.getText().toString().isEmpty()
					|| a.getText().toString().isEmpty()) {
				Toast toast = Toast.makeText(this, R.string.faltakda,
						Toast.LENGTH_SHORT);
				toast.show();
			} else {
				// Formula mia para calcular el nivel de juego
				// (muertes-asesinatos/3-asistencias/7)
				// hay 4 niveles: Bueno, mediocre, malo, Alex
				int total = Integer.parseInt(this.d.getText().toString())
						- (Integer.parseInt(this.k.getText().toString()) / 3)
						- (Integer.parseInt(this.a.getText().toString()) / 7);

				if (total < 3) {
					Toast toast = Toast.makeText(this, R.string.op1,
							Toast.LENGTH_SHORT);
					toast.show();
					totalRepeticiones = 1;
				} else if (total > 3 && total <= 6) {
					Toast toast = Toast.makeText(this, R.string.op2,
							Toast.LENGTH_SHORT);
					toast.show();
					totalRepeticiones = 2;
				} else if (total > 6 && total <= 8) {
					Toast toast = Toast.makeText(this, R.string.op3,
							Toast.LENGTH_SHORT);
					toast.show();
					totalRepeticiones = 3;
				} else {
					Toast toast = Toast.makeText(this, R.string.op4,
							Toast.LENGTH_SHORT);
					toast.show();
					totalRepeticiones = 4;
				}
				// comprobacion mediante texto para saber que opcion del
				// textButton
				// se ha cogido
				int tipoEjercicio = rg.getCheckedRadioButtonId();
				rb = (RadioButton) findViewById(tipoEjercicio);
				// lo pasa por el segundo parametro de la cadena
				if (rb.getText().equals("Entrenamiento blando")
						|| rb.getText().equals("Soft training")
						|| rb.getText().equals("Allenamento facile")) {
					cadena[1] = String.valueOf(1);
				} else if (rb.getText().equals("Entrenamiento medio")
						|| rb.getText().equals("Average training")
						|| rb.getText().equals("Allenamento mezzo")) {
					cadena[1] = String.valueOf(2);
				} else {
					cadena[1] = String.valueOf(3);
				}
				cadena[0] = String.valueOf(totalRepeticiones);
				Intent act = new Intent(this, Ejercicios.class);
				act.putExtra("ejer", cadena);
				startActivity(act);
				finish();
			}
		}

	}

	public int getKills() {
		return Kills;
	}

	public void setKills(int kills) {
		Kills = kills;
	}

	public int getDeaths() {
		return Deaths;
	}

	public void setDeaths(int deaths) {
		Deaths = deaths;
	}

	public int getAssistances() {
		return Assistances;
	}

	public void setAssistances(int assistances) {
		Assistances = assistances;
	}

	protected void ObtenerKDA(String entrada) {
		String json = entrada;
		int indexD = json.indexOf("numDeaths");
		int indexK = json.indexOf("championsKilled");
		int indexA = json.indexOf("assists");

		String dAUX = json.substring(indexD + 11, indexD + 14);
		String kAUX = json.substring(indexK + 17, indexK + 20);
		String aAUX = json.substring(indexA + 9, indexA + 12);
		if (dAUX.contains(",")) {
			StringTokenizer st = new StringTokenizer(dAUX, ",");
			setDeaths(Integer.parseInt(st.nextToken()));
		} else {
			setDeaths(Integer.parseInt(dAUX));
		}
		if (aAUX.contains(",")) {
			StringTokenizer st = new StringTokenizer(aAUX, ",");
			setAssistances(Integer.parseInt(st.nextToken()));
		} else {
			setAssistances(Integer.parseInt(aAUX));
		}
		if (kAUX.contains(",")) {
			StringTokenizer st = new StringTokenizer(kAUX, ",");
			setKills(Integer.parseInt(st.nextToken()));
		} else {
			setKills(Integer.parseInt(kAUX));
		}
	}

	// esta repetida para el default, sino daba error, (Se puede optimizar
	// llamando a este procedimiento arriba, pero solo si ven el codigo)
	public void onRadioButtonClicked(View v) {
		int tipoEjercicio = rg.getCheckedRadioButtonId();
		rb = (RadioButton) findViewById(tipoEjercicio);

		if (rb.getText().equals("Entrenamiento blando")
				|| rb.getText().equals("Soft training")
				|| rb.getText().equals("Allenamento facile")) {
			cadena[1] = String.valueOf(1);
		} else if (rb.getText().equals("Entrenamiento medio")
				|| rb.getText().equals("Average training")
				|| rb.getText().equals("Allenamento mezzo")) {
			cadena[1] = String.valueOf(2);
		} else {
			cadena[1] = String.valueOf(3);
		}
	}

	@SuppressLint("NewApi")
	public void est(View v) {
		Intent act = new Intent(this, Estadisticas.class);
		startActivity(act);
	}

	// guarda los datos en una sola columna y modifica los 9 valores de los
	// ejercicios, si ven codigo se cambia por mas columnas
	// quizas haya un error de creacion de fila nueva cada vez que se abre la
	// app, verlo
	private void crearSQL() {
		String texto = "+a0+b0+c0+d0+e0+f0+g0+h0+i0";
		bd = new AdaptadorBD(this);
		bd.open();
		if (!bd.get(1).isLast()) {
			bd.insertar(texto);
		}

		// Log para ver que muestra bien los datos.
		// Log.d("BASE DE DATOS QUE DEBERIA FUNCIONAR", bd.get(1).getString(1));
		// bd.get(1).getString(1);
		bd.close();
	}
}
