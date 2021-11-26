package com.example.atmasalon.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atmasalon.R;
import com.example.atmasalon.database.DatabaseUser;
import com.example.atmasalon.databinding.FragmentReservation2Binding;
import com.example.atmasalon.entity.User;
import com.example.atmasalon.preferences.ReservationPreference;
import com.example.atmasalon.preferences.UserPreference;

public class FragmentReservation2 extends Fragment implements View.OnClickListener{
    private FragmentReservation2Binding binding;
    private UserPreference userPreference;
    private ReservationPreference reservationPreference;
    private double totalHarga = 0;
    private double hargaModel = 0, hargaWarna = 0;
    private static double hargaJasaSalon = 40000;
    private String modelRambut = "", warnaRambut = "";

    public FragmentReservation2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reservation_2, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reservationPreference = new ReservationPreference(this.getActivity());
        userPreference = new UserPreference(this.getActivity());
        binding.btnBayarReservasi.setOnClickListener(this);

        SetAllRadioOnClick();
        CheckFilled();

        TextView text = getActivity().findViewById(R.id.page_name);
        text.setText("Reservasi");
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnBayarReservasi)
        {
            if(Validation())
            {
                String nama, telp, lokasi, model, warna;
                nama = binding.inputLayoutNamaReservasi.getEditText().getText().toString();
                telp = binding.inputLayoutTelpReservasi.getEditText().getText().toString();
                lokasi = binding.inputLayoutLokasiSalon.getEditText().getText().toString();
                model = GetSelectedModel().trim();
                warna = GetSelectedWarna().trim();
                totalHarga = hargaModel + hargaWarna + hargaJasaSalon;

                reservationPreference.SetFilled();
                reservationPreference.FillDataPage2(lokasi ,nama, telp, model, warna, totalHarga);

                if(GetUser().getSaldo() >= totalHarga)
                {
                    this.getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.layout_fragment, new FragmentPembayaran())
                        .commit();
                }
                else
                {
                    this.getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.layout_fragment, new FragmentPembayaranGagal())
                        .commit();
                }

            }
            else
            {
                Toast.makeText(getActivity(), "Silahkan isi semua field!", Toast.LENGTH_SHORT).show();
            }
        }
        else if(view.getId() == R.id.radio_model_keriting || view.getId() == R.id.radio_model_lurus || view.getId() == R.id.radio_model_mohawk)
        {
            SetRadioModelActive(view, "");
        }
        else if(view.getId() == R.id.radio_warna_hitam || view.getId() == R.id.radio_warna_cokelat || view.getId() == R.id.radio_warna_merah
                || view.getId() == R.id.radio_warna_oranye || view.getId() == R.id.radio_warna_silver)
        {
            SetRadioWarnaActive(view, "");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //jangan lupa Get radio buton
        String nama, telp, lokasi, model, warna;
        nama = binding.inputLayoutNamaReservasi.getEditText().getText().toString();
        telp = binding.inputLayoutTelpReservasi.getEditText().getText().toString();
        lokasi = binding.inputLayoutLokasiSalon.getEditText().getText().toString();
        model = GetSelectedModel().trim();
        warna = GetSelectedWarna().trim();
        totalHarga = hargaModel + hargaWarna + hargaJasaSalon;

        reservationPreference.FillDataPage2(lokasi ,nama, telp, model, warna, totalHarga);
        binding = null;
    }

    private boolean Validation()
    {
        //Validasi untuk model dan warna juga
        String nama, telp;
        nama = binding.inputLayoutNamaReservasi.getEditText().getText().toString();
        telp = binding.inputLayoutTelpReservasi.getEditText().getText().toString();
        if(nama.isEmpty())
        {
            return false;
        }
        else if(telp.isEmpty())
        {
            return false;
        }
        else if(modelRambut.isEmpty())
        {
            return false;
        }
        else if(warnaRambut.isEmpty())
        {
            return false;
        }

        return true;
    }

    private void CheckFilled()
    {
        binding.inputLayoutTelpReservasi.getEditText().setText(reservationPreference.GetAllData().getNoTelp());
        binding.inputLayoutLokasiSalon.getEditText().setText(reservationPreference.GetAllData().getLokasiSalon());
        binding.inputLayoutNamaReservasi.getEditText().setText(reservationPreference.GetAllData().getNamaPemesan());
        SetRadioModelActive(getView(), reservationPreference.GetAllData().getModelRambut());
        SetRadioWarnaActive(getView(), reservationPreference.GetAllData().getWarnaRambut());
    }

    private String GetSelectedModel()
    {
        if(binding.radioModelKeriting.isChecked())
        {
            hargaModel = 50000;
            return getString(R.string.keriting);
        }
        else if(binding.radioModelLurus.isChecked())
        {
            hargaModel = 30000;
            return getString(R.string.lurus);
        }
        else if(binding.radioModelMohawk.isChecked())
        {
            hargaModel = 40000;
            return getString(R.string.mohawk);
        }
        else
        {
            hargaModel = 0;
            return "";
        }
    }

    private String GetSelectedWarna()
    {
        if(binding.radioWarnaCokelat.isChecked())
        {
            hargaWarna = 20000;
            return getString(R.string.cokelat);
        }
        else if(binding.radioWarnaHitam.isChecked())
        {
            hargaWarna = 0;
            return getString(R.string.hitam);
        }
        else if(binding.radioWarnaMerah.isChecked())
        {
            hargaWarna = 60000;
            return getString(R.string.merah);
        }
        else if(binding.radioWarnaOranye.isChecked())
        {
            hargaWarna = 50000;
            return getString(R.string.oranye);
        }
        else if(binding.radioWarnaSilver.isChecked())
        {
            hargaWarna = 100000;
            return getString(R.string.silver);
        }
        else
        {
            hargaWarna = 0;
            return "";
        }
    }

    private void SetAllRadioOnClick()
    {
        binding.radioModelMohawk.setOnClickListener(this);
        binding.radioModelLurus.setOnClickListener(this);
        binding.radioModelKeriting.setOnClickListener(this);

        binding.radioWarnaSilver.setOnClickListener(this);
        binding.radioWarnaHitam.setOnClickListener(this);
        binding.radioWarnaCokelat.setOnClickListener(this);
        binding.radioWarnaMerah.setOnClickListener(this);
        binding.radioWarnaOranye.setOnClickListener(this);
    }

    private void SetRadioModelActive(View view, String model)
    {
        if(view.getId() == R.id.radio_model_keriting || model.equalsIgnoreCase(getString(R.string.keriting)) )
        {
            binding.radioModelKeriting.setChecked(true);
            modelRambut = getString(R.string.keriting);
            binding.radioModelLurus.setChecked(false);
            binding.radioModelMohawk.setChecked(false);
        }
        else if(view.getId() == R.id.radio_model_mohawk || model.equalsIgnoreCase(getString(R.string.mohawk)))
        {
            binding.radioModelMohawk.setChecked(true);
            modelRambut = getString(R.string.mohawk);
            binding.radioModelLurus.setChecked(false);
            binding.radioModelKeriting.setChecked(false);
        }
        else if(view.getId() == R.id.radio_model_lurus || model.equalsIgnoreCase(getString(R.string.lurus)))
        {
            binding.radioModelLurus.setChecked(true);
            modelRambut = getString(R.string.lurus);
            binding.radioModelMohawk.setChecked(false);
            binding.radioModelKeriting.setChecked(false);
        }
        else
        {
            modelRambut = "";
            binding.radioModelMohawk.setChecked(false);
            binding.radioModelLurus.setChecked(false);
            binding.radioModelKeriting.setChecked(false);
        }
    }

    private void SetRadioWarnaActive(View view, String warna)
    {
        if(view.getId() == R.id.radio_warna_hitam || warna.equalsIgnoreCase(getString(R.string.hitam)))
        {
            binding.radioWarnaHitam.setChecked(true);
            warnaRambut = getString(R.string.hitam);
            binding.radioWarnaMerah.setChecked(false);
            binding.radioWarnaOranye.setChecked(false);
            binding.radioWarnaSilver.setChecked(false);
            binding.radioWarnaCokelat.setChecked(false);
        }
        else if(view.getId() == R.id.radio_warna_merah || warna.equalsIgnoreCase(getString(R.string.merah)))
        {
            binding.radioWarnaMerah.setChecked(true);
            warnaRambut = getString(R.string.merah);
            binding.radioWarnaHitam.setChecked(false);
            binding.radioWarnaOranye.setChecked(false);
            binding.radioWarnaSilver.setChecked(false);
            binding.radioWarnaCokelat.setChecked(false);
        }
        else if(view.getId() == R.id.radio_warna_silver || warna.equalsIgnoreCase(getString(R.string.silver)))
        {
            binding.radioWarnaSilver.setChecked(true);
            warnaRambut = getString(R.string.silver);
            binding.radioWarnaMerah.setChecked(false);
            binding.radioWarnaOranye.setChecked(false);
            binding.radioWarnaHitam.setChecked(false);
            binding.radioWarnaCokelat.setChecked(false);
        }
        else if(view.getId() == R.id.radio_warna_cokelat || warna.equalsIgnoreCase(getString(R.string.cokelat)))
        {
            binding.radioWarnaCokelat.setChecked(true);
            warnaRambut = getString(R.string.cokelat);
            binding.radioWarnaMerah.setChecked(false);
            binding.radioWarnaOranye.setChecked(false);
            binding.radioWarnaSilver.setChecked(false);
            binding.radioWarnaHitam.setChecked(false);
        }
        else if(view.getId() == R.id.radio_warna_oranye || warna.equalsIgnoreCase(getString(R.string.oranye)))
        {
            binding.radioWarnaOranye.setChecked(true);
            warnaRambut = getString(R.string.oranye);
            binding.radioWarnaMerah.setChecked(false);
            binding.radioWarnaHitam.setChecked(false);
            binding.radioWarnaSilver.setChecked(false);
            binding.radioWarnaCokelat.setChecked(false);
        }
        else
        {
            warnaRambut = "";
            binding.radioWarnaMerah.setChecked(false);
            binding.radioWarnaHitam.setChecked(false);
            binding.radioWarnaSilver.setChecked(false);
            binding.radioWarnaOranye.setChecked(false);
            binding.radioWarnaCokelat.setChecked(false);
        }
    }

    private User GetUser()
    {
        return DatabaseUser.GetInstance(getActivity().getApplicationContext()).GetDatabase().userDao().GetUser(userPreference.GetUserID());
    }
}