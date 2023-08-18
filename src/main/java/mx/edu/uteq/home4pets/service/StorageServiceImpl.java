package mx.edu.uteq.home4pets.service;

import com.jcraft.jsch.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Service
public class StorageServiceImpl implements IStorageService{

    private ChannelSftp channelSftp;



    @PostConstruct
    public void setUpSsh() throws JSchException {
        JSch jsch = new JSch();
        Session jschSession = jsch.getSession("root",
                "74.208.25.75");
        jschSession.setPassword("O!eKp%$F1e");
        jschSession.setConfig("StrictHostKeyChecking", "no");
        jschSession.connect();
        this.channelSftp = (ChannelSftp)jschSession.openChannel("sftp");
    }
    @Override
    public boolean almacenarApache(MultipartFile ine, MultipartFile cmp, String ineName, String cmpName) {
        try {
            if (ine.isEmpty()||cmp.isEmpty()) {
                return false;
            }
            try  {
                InputStream ineIS = ine.getInputStream();
                InputStream cmpIS = cmp.getInputStream();
                this.channelSftp.connect();
                this.channelSftp.put(ineIS, "/var/www/html/images/home4pets/"+ineName);
                this.channelSftp.put(cmpIS, "/var/www/html/images/home4pets/"+cmpName);
                //this.channelSftp.rename(oldFilePath, newFilePath);
            } catch (SftpException e) {
                return false;
            }
        }
        catch (IOException | JSchException e) {
            return false;
        }finally{
            //this.channelSftp.disconnect();
        }
        return true;
    }
}
