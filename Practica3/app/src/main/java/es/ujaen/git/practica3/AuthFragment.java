package es.ujaen.git.practica3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Subclase de Fragment.
 * @param *ARG_PARAM1 primer  parámetro.
 * @param *ARG_PARAM2 segundo parámetro.
 * @param *ARG_PARAM3 tercer  parámetro.
 */
public class AuthFragment extends Fragment {
    // El fragmento inicializa los parámetros.
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    //Renombrar y cambiar los tipos de los parámetros.
    private String mUser="";
    private String mPass="";
    private String mIP="";

    //Se inicializan los editables.
    private EditText mEditUser=null;
    private EditText mEditPass=null;
    private EditText mEditIP=null;

    //Constructor requerido por el fragmento.
    public AuthFragment() {

    }

    /**
     * Se usa este método para crear una nueva instancia del fragmento usando los parámetros entregados.
     * @param *param1 user.
     * @param *param2 pass.
     * @param *param3 ip.
     * @return una nueva instancia de fragment AuthFragment.
     */
    public static AuthFragment newInstance(String user, String pass, String ip) {
        AuthFragment fragment = new AuthFragment();
        Bundle args = new Bundle();                 //Creamos args de tipo Bundle como nuevo Bundle.
        args.putString(ARG_PARAM1, user);           //Guardamos en args una cadena con los parámetros
        args.putString(ARG_PARAM2, pass);           //que colocamos como una cadena string.
        args.putString(ARG_PARAM3, ip);
        fragment.setArguments(args);                //Guardamos el bundle args con las cadenas en fragment.
        return fragment;
    }//Fin nueva instancia.

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null)
        if (getArguments() != null) { //Compruebo que la instancia de fragment AuthFragment no está vacía.
            mUser = getArguments().getString(ARG_PARAM1); //Obtengo cada una de las cadenas de args.
            mPass = getArguments().getString(ARG_PARAM2);
            mIP   = getArguments().getString(ARG_PARAM3);
        }//Fin If.
    }//Fin de OnCreate.

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(savedInstanceState != null){ //Si el estado es diferente de null, es decir cambia el estado del fragmento.
            Toast.makeText(getActivity(),"Cambio de Configuración",Toast.LENGTH_SHORT).show();
        }//Fin if comprobación cambio de estado del fragmento.

        // Inflate el layout de este fragmento.
        View fragmento = inflater.inflate(R.layout.fragment_auth, container, false);

        mEditUser = (EditText)fragmento.findViewById(R.id.auth_edit_user); //Busco cada EditText por su id en el layout y
        mEditPass = (EditText)fragmento.findViewById(R.id.auth_edit_pass); //los guardo en las variables tipo editables que
        mEditIP   = (EditText)fragmento.findViewById(R.id.auth_edit_ip);   //he inicializado arriba.

        mEditUser.setText(mUser);
        mEditPass.setText(mPass);
        mEditIP.setText(mIP);

        Button boton = (Button)fragmento.findViewById(R.id.auth_button_send); //Busco el botón por su Id y los formateo.

        boton.setOnClickListener(new View.OnClickListener() {  //Como estoy en el fragment estoy atento al evento click de este.
        @Override
        public void onClick(View v) { //Cuando pulso el botón.
            String user   = mEditUser.getText().toString(); //Convierto el editable de EditUser en un string user.
            String pass   = mEditPass.getText().toString(); //Convierto el editable de EditUser en un string pass.
            String ip     = mEditIP.getText().toString();   //Convierto el editable de EditUser en un string ip.
            Autenticacion datos = new Autenticacion(user,pass,ip);//Introduzco las variables en la clase Autenticacion al instanciarla.
            //Utilizo un intent para realizar transición de fragmento a Actividad pasando los datos para ello.
            Intent i = new Intent(getActivity(), ConexActivity.class);//Tomo el fragmento actual, la actividad a la que quiero ir.
            i.putExtra("usuario", user);//paso el valor de user con id usuario.
            i.putExtra("password", pass);//paso el valor de pass con id password.
            i.putExtra("direccionIp", ip);//paso el valor de ip con id direccionIp.
            startActivity(i);//Realizar la transición intent con identificador i.
        }//Fin del método click.
    });//Fin de la escucha del evento click.

        return fragmento;

    }//Fin del createView.

}//Fin clase AuthFragment.
