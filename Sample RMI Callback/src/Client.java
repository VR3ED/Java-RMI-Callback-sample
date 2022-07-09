import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

//16: aggiungo implementazione RemoteListener
//18: estendo con UnicastRemoteObject
public class Client extends UnicastRemoteObject implements RemoteListener {
	
	//private static final long serialVersionUID = 1L;

	protected Client() throws RemoteException {
		super();
	}

	//6: si crea il main del client
	public static void main(String[] args) {
		try {
			//7: si inizializza il registro
			Registry registro = LocateRegistry.getRegistry(6969);
			//8: si preleva il servizio dal registry
			ICurrencyConverter skeleton = (ICurrencyConverter) registro.lookup("CurrencyServer");	
			//9: si posso usare nel client tutti i metodi dell'implementazione del serve
			//System.out.println( skeleton.toEur(1)+"" );
			
			//19: creaiamo un nuovo oggetto capace di essere esportato, questo client verrà aggiunto alla lista di clienti iscritti alla newsletter
			Client obj = new Client();
			skeleton.addListener(obj);
			
			//20: chiamo un po' di volte 
			for(int i=1; i<6; i++) {
				System.out.println(skeleton.toEur(i));
				System.out.println(skeleton.toUSD(i));
			}
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	//17: implemnto il metodo per riceve il parametro che invia il server
	@Override
	public void RemoteEvent(Object param) throws RemoteException {
		System.out.println( "Numero di chiamate effettuate: " + param );
	}
}
