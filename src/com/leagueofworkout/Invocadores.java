package com.leagueofworkout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.util.Log;
import main.java.riotapi.RiotApi;
import main.java.riotapi.RiotApiException;
import constant.Region;
import dto.Game.Game;
import dto.MatchHistory.PlayerHistory;
import dto.Summoner.Summoner;
import com.google.gson.*;

//clase que deberia funcionar, pero parece que la api de java no quiere tirar, igual quiero seguir viendo
public class Invocadores {

	private RiotApi api;
	private Map<String, Summoner> summoners;
	private String nombre;
	private List<Invocador> lista;

	public class Invocador {
		long id;
		long icono;
		String name;
		Region region;
		int[] arrayKDA;

		public Invocador(Region reg, long i, long ico, String n)
				throws RiotApiException {
			region = reg;
			id = i;
			icono = ico;
			name = n;
			arrayKDA = ObtenerKDA(region, id);
		}

		@Override
		public String toString() {
			return "Invocador [id=" + id + ", icono=" + icono + ", name="
					+ name + ", region=" + region + ", arrayKDA="
					+ Arrays.toString(arrayKDA) + "]";
		}

		// TODO: toString
	}

	public Invocadores(RiotApi apiKey) {
		api = apiKey;
		lista = new ArrayList<Invocador>();
	}



	public String ObtenerInvocador(Region reg, String NombreInvocador)
			throws RiotApiException {
		String error = "";
		// Log.d("VERQUERECIBE", reg+"-"+NombreInvocador);
		try {
			//TODO: Aqui esta el problemas
			summoners = api.getSummonerByName(reg, NombreInvocador);
		} catch (RiotApiException e) {
			error = e.getMessage();
		} catch (Exception e) {
			error = e.getMessage();
		}

		if (error != null && summoners!=null) {
			Iterator it = summoners.keySet().iterator();
			nombre = (String) it.next();
			Summoner summoner = summoners.get(nombre);
			long id = summoner.getId();
			lista.add(new Invocador(reg, id, summoner.getProfileIconId(),
					summoner.getName()));
		}
		return error;
	}

	private int[] ObtenerKDA(Region reg, long id) {
		// PlayerHistory player = api.getMatchHistory(reg, id);
		Game game;
		String error = "";
		int[] kda;
		try {
			game = api.getRecentGames(reg, id).getGames().iterator().next();
			kda = new int[] { game.getStats().getChampionsKilled(),
					game.getStats().getNumDeaths(),
					game.getStats().getAssists() };// {k,d,a}
		} catch (RiotApiException e) {
			error = e.getMessage();
			kda = new int[] { -1, -1, -1 };
		}
		return kda;
	}

	public List<Invocador> devolverLista() {
		return this.lista;
	}
}
