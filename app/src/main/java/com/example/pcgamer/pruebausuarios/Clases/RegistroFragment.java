package com.example.pcgamer.pruebausuarios.Clases;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pcgamer.pruebausuarios.R;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegistroFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegistroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistroFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int COD_SELECCIONA = 10;
    private static final int COD_FOTO = 20;

    private static final String CARPETA_PRINCIPAL= "MisImagenesApp/";
    private static final String CARPETA_IMAGEN = "imagenes";
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;

    private String path;
    File fileImagen;
    Bitmap bitmap;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    EditText CampoNombre, CampoDireccion, CampoImagen;
    Button btnRegistro, btnFoto;
    ImageView imgFoto;

    ProgressDialog progreso;



    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    StringRequest stringRequest;


    public RegistroFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistroFragment newInstance(String param1, String param2) {
        RegistroFragment fragment = new RegistroFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_registro, container, false);
        CampoNombre = (EditText) vista.findViewById(R.id.CampoNombre);
        CampoDireccion = (EditText) vista.findViewById(R.id.CampoDireccion);
        //CampoImagen = (EditText) vista.findViewById(R.id.CampoImagen);

        btnRegistro = (Button) vista.findViewById(R.id.btnRegistrar);
        btnFoto = (Button) vista.findViewById(R.id.btnFoto);

        imgFoto = (ImageView) vista.findViewById(R.id.imgFoto);



        String url;

        request = Volley.newRequestQueue(getContext());



        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService();
            }
        });

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogOpciones();
            }
        });


        //inflater.inflate(R.layout.fragment_registro, container, false
        return vista;
    }

    private void mostrarDialogOpciones() {
        final CharSequence [] opciones ={"Elegir de galeria","Cacelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Elige una opcion");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar foto")){

                    abrirCamara();


                }else{
                    if (opciones[i].equals("Elegir de galeria")){
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent,"Seleccione"),COD_SELECCIONA);

                    }else {
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        builder.show();
    }

    private void abrirCamara() {
        File miFile = new File(Environment.getExternalStorageDirectory(),DIRECTORIO_IMAGEN);
        boolean isCreada=miFile.exists();

        if (isCreada==false){
            isCreada=miFile.mkdirs();
        }

        if (isCreada==true){
            Long consecutivo = System.currentTimeMillis()/1000;
            String nombre=consecutivo.toString()+".jpg";

            path=Environment.getExternalStorageDirectory()+File.separator+DIRECTORIO_IMAGEN
                    +File.separator+nombre;

            fileImagen= new File(path);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImagen));

            startActivityForResult(intent, COD_FOTO);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case COD_SELECCIONA:
                Uri miPath=data.getData();
                imgFoto.setImageURI(miPath);
                try {
                    bitmap=MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),miPath);
                    imgFoto.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;

            case COD_FOTO:
                Toast.makeText(getContext(), "Tomar foto",Toast.LENGTH_SHORT).show();
                MediaScannerConnection.scanFile(getContext(), new String[]{path}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String s, Uri uri) {
                                Log.i("Path",""+path);
                            }
                        });

                bitmap = BitmapFactory.decodeFile(path);
                imgFoto.setImageBitmap(bitmap);

                break;

        }
    }

    private void cargarWebService() {
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();

        String url="https://e66af388.ngrok.io/image?";

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progreso.hide();
                if (response.trim().equalsIgnoreCase("registra")){
                    CampoNombre.setText("");
                    CampoDireccion.setText("");
                    imgFoto.setImageBitmap(null);

                    Toast.makeText(getContext(), "Se ha registrado exitosamente",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "No se pudo registrar",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se ha podido conectar",Toast.LENGTH_SHORT).show();
                progreso.hide();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String nombre = CampoNombre.getText().toString();
                String direccion = CampoDireccion.getText().toString();

                String imagen= convertirImgString(bitmap);

                Map<String,String> parametros = new HashMap<>();
                parametros.put("nombre", nombre);
                parametros.put("direccion", direccion);
                parametros.put("imagen", imagen);

                return parametros;
            }
        };

        request.add(stringRequest);
    }

    private String convertirImgString(Bitmap bitmap) {

        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, array);
        byte [] imagenByte = array.toByteArray();
        String imagenString = Base64.encodeToString(imagenByte, Base64.DEFAULT);


        return imagenString;

    }




    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
