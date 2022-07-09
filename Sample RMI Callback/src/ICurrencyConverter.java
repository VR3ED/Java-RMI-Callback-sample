import java.rmi.Remote;

/* con RMI callback aggiungiamo IN QUESTO CASO un metodo per incrementare un contatore 
 * ogni volta che viene richiamato un servizio del server 
 * inviamo una notifica (come fosse una newsletter) a 
 * tutti gli utenti che si sono iscirtti per le callback.
 * nella notifica è contenuto il numero di volte totali che 
 * il server ha eseguito un servizio*/

import java.rmi.RemoteException;

//1: estende remote
public interface ICurrencyConverter extends Remote{
	//2 i metodi fanno throws
	float toEur(float usd) throws RemoteException;
	float toUSD(float eur)  throws RemoteException;
	
	//10: aggiungiamo 2 metodi: uno per l'iscrizione alla newsletter e uno per rimuove un client dalla newsletter
	void addListener(RemoteListener listener) throws RemoteException;     
	void removeListener(RemoteListener listener) throws RemoteException;
}
