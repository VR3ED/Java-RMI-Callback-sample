import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;


public class Server extends UnicastRemoteObject implements ICurrencyConverter{

	//12: aggiungo variabile numero chiamate (perchè la traccia vuole che nel client vengano notificate tutte le chiamate, con questa variabile ne teniamo il conto)
	private int numChiamate = 0;
	//13: aggiunto una lista di remote listener per tenere traccia dei client che si sono iscritti alla newsletter
	private List<RemoteListener> listeners; 
	
	public Server() throws RemoteException {
		super();
		//13.2 nel costruttore si inizializza la lista di Client
		listeners = new ArrayList<RemoteListener>();
	}

	//3: implementare metodi 
	@Override
	public float toEur(float usd) throws RemoteException {
		numChiamate++;
		notifyListeners();
		return usd*0.97f;
	}

	@Override
	public float toUSD(float eur) throws RemoteException {
		numChiamate++;
		notifyListeners();
		return eur*1.06f;
	}
	
	public static void main(String[] args) {
		try {
			Server obj = new Server();
			//4: nel try inizializzare stub e registry (qeusto va' fatto se volgiamo avere una classe server separate dall'oggetto che implementa l'interfaccia)
			//ICurrencyConverter stub = (ICurrencyConverter) UnicastRemoteObject.exportObject(obj,6969);
			Registry registro = LocateRegistry.createRegistry(6969);
			
			//5: fai il rebind per registrare il servizio online 
			registro.rebind("CurrencyServer",obj); //il secondo parametro sarebbe stub  se volgiamo avere una classe server separate dall'oggetto che implementa l'interfaccia
			System.out.println("servizio online");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	//14: faccio override dei due metodi di iscrizione e rimozione
	@Override
	public void addListener(RemoteListener listener) throws RemoteException {
		listeners.add(listener);
	}
	@Override
	public void removeListener(RemoteListener listener) throws RemoteException {
		listeners.remove(listener);
	}

	//15: aggiungo metodo esclusivo del server che server a notificare i client
	private void notifyListeners(){
		//16: in un ciclo for each
		for(RemoteListener p: listeners) {
			//faccio try cactch in cui per ogni client richiamo il metodo remote event per far 
			try {
				p.RemoteEvent(numChiamate);
			} catch (RemoteException e) {
				e.printStackTrace();
				//se non riesco a inviare la notifica al client allora lo elimino dalla lista
				listeners.remove(p);
			}
		}
	}

}
