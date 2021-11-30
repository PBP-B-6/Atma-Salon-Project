package com.example.atmasalon;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Context;

import com.example.atmasalon.unitTesting.UserPresenter;
import com.example.atmasalon.unitTesting.UserService;
import com.example.atmasalon.unitTesting.UserView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RegisterActivityTest {
    @Mock
    private UserView view;
    @Mock
    private UserService service;
    @Mock
    private Context con;

    private UserPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new UserPresenter(view, service, con);
    }

    @Test
    public void shouldShowErrorMessageWhenNamaIsEmpty() throws Exception {
        when(view.getNama()).thenReturn("");
        System.out.println("Testing Pertama: Inputan Nama Kosong");
        System.out.println("Nama: " + view.getNama());

        presenter.onProfilClicked();
        verify(view).showNamaError("Nama tidak boleh kosong");
    }

    @Test
    public void shouldShowErrorMessageWhenEmailIsEmpty() throws Exception {
        System.out.println("\n\nTesting Kedua: Inputan Email Kosong");

        when(view.getNama()).thenReturn("Yotam");
        System.out.println("Nama: " + view.getNama());

        when(view.getEmail()).thenReturn("");
        System.out.println("Email: " + view.getEmail());

        presenter.onProfilClicked();
        verify(view).showEmailError("Email tidak boleh kosong");
    }

    @Test
    public void shouldShowErrorMessageWhenEmailNotMatch() throws Exception {
        System.out.println("\n\nTesting Ketiga: Inputan Email Tidak Sesuai Format");

        when(view.getNama()).thenReturn("Yotam");
        System.out.println("Nama: " + view.getNama());

        when(view.getEmail()).thenReturn("YOSUSU@");
        System.out.println("Email: " + view.getEmail());

        presenter.onProfilClicked();
        verify(view).showEmailError("Email harus memenuhi format");
    }

    @Test
    public void shouldShowErrorMessageWhenPassword1IsEmpty() throws Exception {
        System.out.println("\n\nTesting Keempat: Inputan Password 1 Kosong");

        when(view.getNama()).thenReturn("Yotam");
        System.out.println("Nama: " + view.getNama());

        when(view.getEmail()).thenReturn("Yohtam@gmail.com");
        System.out.println("Email: " + view.getEmail());

        when(view.getPassword1()).thenReturn("");
        System.out.println("Password: " + view.getPassword1());

        presenter.onProfilClicked();
        verify(view).showPassword1Error("Password tidak boleh kosong");
    }

    @Test
    public void shouldShowErrorMessageWhenPassword2IsEmpty() throws Exception {
        System.out.println("\n\nTesting Kelima: Inputan Password 2 Kosong");

        when(view.getNama()).thenReturn("Yotam");
        System.out.println("Nama: " + view.getNama());

        when(view.getEmail()).thenReturn("Ytamamsasm@gmail.com");
        System.out.println("Email: " + view.getEmail());

        when(view.getPassword1()).thenReturn("yotam1");
        System.out.println("Password: " + view.getPassword1());

        when(view.getPassword2()).thenReturn("");
        System.out.println("Password: " + view.getPassword2());

        presenter.onProfilClicked();
        verify(view).showPassword2Error("Password tidak boleh kosong");
    }

    @Test
    public void shouldShowErrorMessageWhenPasswordDoesntMatch() throws Exception {
        System.out.println("\n\nTesting Keenam: Inputan Password tidak sama");

        when(view.getNama()).thenReturn("Yotam");
        System.out.println("Nama: " + view.getNama());

        when(view.getEmail()).thenReturn("Ytamamsasm@gmail.com");
        System.out.println("Email: " + view.getEmail());

        when(view.getPassword1()).thenReturn("yotam1");
        System.out.println("Password: " + view.getPassword1());

        when(view.getPassword2()).thenReturn("yotamm");
        System.out.println("Password: " + view.getPassword2());

        presenter.onProfilClicked();
        verify(view).showPasswordMatchingError("Password yang diisikan harus sama");
    }

    @Test
    public void shouldShowErrorMessageWhenJenisKelaminEmpty() throws Exception {
        System.out.println("\n\nTesting Ketuju: Inputan Jenis Kelamin kosong");

        when(view.getNama()).thenReturn("Yotam");
        System.out.println("Nama: " + view.getNama());

        when(view.getEmail()).thenReturn("Ytamamsasm@gmail.com");
        System.out.println("Email: " + view.getEmail());

        when(view.getPassword1()).thenReturn("yotam1");
        System.out.println("Password: " + view.getPassword1());

        when(view.getPassword2()).thenReturn("yotam1");
        System.out.println("Password: " + view.getPassword2());

        when(view.getKelaminValue()).thenReturn(-1);
        System.out.println("Kelamin: " + view.getKelaminValue());

        presenter.onProfilClicked();
        verify(view).showKelaminError("Jenis kelamin harus dipilih");
    }

    @Test
    public void shouldShowErrorMessageWhenPhoneEmpty() throws Exception {
        System.out.println("\n\nTesting Kedelapan: Inputan Telepon kosong");

        when(view.getNama()).thenReturn("Yotam");
        System.out.println("Nama: " + view.getNama());

        when(view.getEmail()).thenReturn("Ytamamsasm@gmail.com");
        System.out.println("Email: " + view.getEmail());

        when(view.getPassword1()).thenReturn("yotam1");
        System.out.println("Password: " + view.getPassword1());

        when(view.getPassword2()).thenReturn("yotam1");
        System.out.println("Password: " + view.getPassword2());

        when(view.getKelaminValue()).thenReturn(1);
        System.out.println("Kelamin: " + view.getKelaminValue());

        when(view.getPhone()).thenReturn("");
        System.out.println("Phone: " + view.getPhone());

        presenter.onProfilClicked();
        verify(view).showPhoneError("Nomor Telepon tidak boleh kosong");
    }

    @Test
    public void shouldShowErrorMessageWhenPhoneLengthFalse() throws Exception {
        System.out.println("\n\nTesting Kesembilan: Inputan Telepon Lengthnya tidak sesuai");

        when(view.getNama()).thenReturn("Yotam");
        System.out.println("Nama: " + view.getNama());

        when(view.getEmail()).thenReturn("Ytamamsasm@gmail.com");
        System.out.println("Email: " + view.getEmail());

        when(view.getPassword1()).thenReturn("yotam1");
        System.out.println("Password: " + view.getPassword1());

        when(view.getPassword2()).thenReturn("yotam1");
        System.out.println("Password: " + view.getPassword2());

        when(view.getKelaminValue()).thenReturn(1);
        System.out.println("Kelamin: " + view.getKelaminValue());

        when(view.getPhone()).thenReturn("081");
        System.out.println("Phone: " + view.getPhone());

        presenter.onProfilClicked();
        verify(view).showPhoneError("Nomor Telepon minimal 10 dan maximal 13");
    }

    @Test
    public void shouldShowErrorMessageWhenPhoneFormatFalse() throws Exception {
        System.out.println("\n\nTesting Kesepuluh: Inputan Telepon Tidak sesuai format");

        when(view.getNama()).thenReturn("Yotam");
        System.out.println("Nama: " + view.getNama());

        when(view.getEmail()).thenReturn("Ytamamsasm@gmail.com");
        System.out.println("Email: " + view.getEmail());

        when(view.getPassword1()).thenReturn("yotam1");
        System.out.println("Password: " + view.getPassword1());

        when(view.getPassword2()).thenReturn("yotam1");
        System.out.println("Password: " + view.getPassword2());

        when(view.getKelaminValue()).thenReturn(1);
        System.out.println("Kelamin: " + view.getKelaminValue());

        when(view.getPhone()).thenReturn("028213812232");
        System.out.println("Phone: " + view.getPhone());

        presenter.onProfilClicked();
        verify(view).showPhoneError("Nomor Telepon harus memenuhi format");
    }
}