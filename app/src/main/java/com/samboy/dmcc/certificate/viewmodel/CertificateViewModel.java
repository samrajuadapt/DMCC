package com.samboy.dmcc.certificate.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Environment;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.samboy.dmcc.auth.model.User;
import com.samboy.dmcc.auth.ui.LoginActivity;
import com.samboy.dmcc.certificate.repo.CertificateRepository;
import com.samboy.dmcc.jobs.model.Job;

import java.io.File;
import java.io.FileOutputStream;

public class CertificateViewModel extends ViewModel {

    public MutableLiveData<User> user = new MutableLiveData<>();
    public MutableLiveData<Job> jobs = new MutableLiveData<>();

    private CertificateRepository repository;

    public CertificateViewModel(CertificateRepository repository){
        this.repository = repository;
        user = repository.getUser();
        jobs = repository.getJob();
    }

    public void logOut(Context context){
        repository.logOut();
        context.startActivity(new Intent(context, LoginActivity.class));
    }


    public void fetchUserJob(){
        repository.fetchUserJob();
    }

    public void sendPdf(View view){

        PdfDocument myPdfDocument = new PdfDocument();
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(300,600,1).create();
        PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);

        Paint myPaint = new Paint();
        String myString = "Welcome to Company as "+jobs.getValue().getJob();
        int x = 10, y=25;

        for (String line:myString.split("\n")){
            myPage.getCanvas().drawText(line, x, y, myPaint);
            y+=myPaint.descent()-myPaint.ascent();
        }

        myPdfDocument.finishPage(myPage);

        String myFilePath = Environment.getExternalStorageDirectory().getPath() + "/MyCertificate.pdf";
        File myFile = new File(myFilePath);
        try {
            myPdfDocument.writeTo(new FileOutputStream(myFile));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        myPdfDocument.close();

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("application/pdf");
        shareIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { user.getValue().getEmail() });
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Certificate ");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Certificate");
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(myFilePath));
        view.getContext().startActivity(shareIntent);
    }

}
