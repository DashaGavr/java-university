package com.mmoi.javauniversity.controller;

import com.mmoi.javauniversity.models.Session;
import com.mmoi.javauniversity.models.SessionEntity;
import com.mmoi.javauniversity.repo.SessionEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.util.*;
import java.util.stream.Stream;

@Controller
public class MainController {

    static ArrayList<String> ref_paths = new ArrayList<>();
    static ArrayList<String> dist_paths = new ArrayList<>();

    static {
        String cwd = System.getProperty("user.dir");
        System.out.println("Current working directory : " + cwd);
        File[] ref_tmp = new File("src/main/resources/static/images/ref_images").listFiles();
        File[] dist_tmp = new File("src/main/resources/static/images/databaseImage").listFiles();
        //InSessionsId = new HashMap<>();
        try (Stream<File> tmp_paths = Arrays.stream(ref_tmp).sequential()) {
            tmp_paths
                    .forEach(i -> ref_paths.add(i.getName()));
        }

        try (Stream<File> tmp_paths = Arrays.stream(dist_tmp).sequential()) {
            tmp_paths
                    .forEach(i -> dist_paths.add(i.getName()));
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    @Autowired
    private SessionEntityRepository sessionRepository;

    private SessionEntity sessionEntity;

    private Session session;

    private  HashMap<Long, Long> InSessionsId;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Main page");
        return "home";
    }

    @GetMapping("/home")
    public String home2(Model model) {
        model.addAttribute("title", "Main page");
        return "home";
    }

    @GetMapping("/start")
    public String GetStartSession(Model model) {
        Iterable<Session> sessions = sessionRepository.findAll();
        model.addAttribute("sessions", sessions);
        model.addAttribute("title", "Start");
        return "start";
    }

    @PostMapping("/start")
    public String StartSession(Model model) {
        session = new Session();
        //InSessionsId = new HashMap<>();
        //InSessionsId.put(session.getId(), 1L);
        sessionEntity = new SessionEntity();
        //sessionEntity.setSession_id(session.getId());
        ArrayList<SessionEntity> tmp = new ArrayList<SessionEntity>();
        tmp.add(sessionEntity);
        session.setSessions(tmp);

        sessionEntity.setInSessionId((long) session.getSessionSize());
        //sessionEntity.setInSessionId(InSessionsId.get(session.getId()));


        String ref = generateRefImage();
        model.addAttribute("ref", "/images/ref_images/" + ref);
        String dist1 = generateImage(ref);//"images/databaseImage/I01_0.083_0.6206_13.7056.bmp";
        model.addAttribute("dist1", "/images/databaseImage/" + dist1);
        String dist2 = generateImage(ref);//"images/databaseImage/I01_0.083_0.6206_13.7056.bmp";
        model.addAttribute("dist2", "/images/databaseImage/" + dist2);

        sessionEntity.setRefImgName(ref);
        sessionEntity.setDistImgName1(dist1);
        sessionEntity.setDistImgName2(dist2);
        sessionEntity.setStart(new Date());
        sessionEntity.setSession(session);
        //sessionRepository.save(session);
        return "start";
    }

    @PostMapping("/nextimage")
    public String NextImage(@RequestParam String chosen, Model model) {

        sessionEntity.setStop(new Date());
        sessionEntity.setChosen(chosen);

        System.out.println(chosen);

        //sessionRepository.save(session);

        sessionEntity = new SessionEntity();

        System.out.println("ses size" + session.getSessionSize());
        //InSessionsId.replace(session.getId(), InSessionsId.get(session.getId()) + 1);
        //System.out.println(InSessionsId.get(session.getId()) + 1);
        //sessionEntity.setInSessionId(InSessionsId.get(session.getId()));

        String ref = generateRefImage();
        model.addAttribute("ref", "/images/ref_images/" + ref);
        String dist1 = generateImage(ref);//"images/databaseImage/I01_0.083_0.6206_13.7056.bmp";
        model.addAttribute("dist1", "/images/databaseImage/" + dist1);
        String dist2 = generateImage(ref);//"images/databaseImage/I01_0.083_0.6206_13.7056.bmp";
        model.addAttribute("dist2", "/images/databaseImage/" + dist2);

        sessionEntity.setRefImgName(ref);
        sessionEntity.setDistImgName1(dist1);
        sessionEntity.setDistImgName2(dist2);
        sessionEntity.setStart(new Date());
        session.addSessionEntity(sessionEntity);
        sessionEntity.setInSessionId((long) session.getSessionSize());
        sessionEntity.setSession(session);

        return "start";
    }

    public String generateRefImage() {
        Random rand = new Random();
        int index = rand.nextInt(ref_paths.size());

        System.out.println("index_ref " + index + "  size " + ref_paths.size());

        return ref_paths.get(index);
        //return "images/databaseImage/I01_0.0061_3.0459_11.0474.bmp";
    }

    public String generateImage(String ref) {
        Random rand = new Random();
        ArrayList<String> tmp = new ArrayList<String>();

        System.out.println(ref.split("\\.")[0]);

        dist_paths.stream().filter(i -> i.contains(ref.split("\\.")[0])).forEach(tmp::add);
        int index = rand.nextInt(tmp.size());

        System.out.println("index_dist  " + index + "  size " + tmp.size());

        return tmp.get(index);          //"images/databaseImage/I01_0.2719_3.7582_11.9509.bmp";
    }

    @PostMapping("/stop")
    public String StopSession(Model model)
    {
        System.out.println("stop");
        model.addAttribute("title", "Main page");
        sessionEntity.setStop(new Date());
        sessionRepository.save(session);
        return "redirect:/";
    }

    @GetMapping("/stop")
    public String StopSessionWithEsc(Model model)
    {
        System.out.println("esc");
        model.addAttribute("title", "Main page");
        sessionEntity.setStop(new Date());
        sessionRepository.save(session);
        return "redirect:/";
    }

    @GetMapping("/error")
    public String Error(Model model)
    {

        model.addAttribute("title", "Error page");
        return "redirect:/";
    }

/*

    @PostMapping("/start/1")
    public String NextImages( Model model)
    {
        session.setChosen("1");
        sessionRepository.save(session);
        String ref = generateRefImage();
        model.addAttribute("ref", "/images/ref_images/" + ref);
        String dist1 =  generateImage(ref);//"images/databaseImage/I01_0.083_0.6206_13.7056.bmp";
        model.addAttribute("dist1", "/images/databaseImage/" + dist1);
        String dist2 =  generateImage(ref);//"images/databaseImage/I01_0.083_0.6206_13.7056.bmp";
        model.addAttribute("dist2", "/images/databaseImage/" + dist2);
        session = new SessionEntity();
        session.setRefImgName(ref);
        session.setDistImgName1(dist1);
        session.setDistImgName2(dist2);
        //sessionRepository.save(session);
        return "start";
    }
    @PostMapping("/start/2")
    public String NextImages2( Model model)
    {
        session.setChosen("2");
        sessionRepository.save(session);
        String ref = generateRefImage();
        model.addAttribute("ref", "/images/ref_images/" + ref);
        String dist1 =  generateImage(ref);//"images/databaseImage/I01_0.083_0.6206_13.7056.bmp";
        model.addAttribute("dist1", "/images/databaseImage/" + dist1);
        String dist2 =  generateImage(ref);//"images/databaseImage/I01_0.083_0.6206_13.7056.bmp";
        model.addAttribute("dist2", "/images/databaseImage/" + dist2);
        session = new SessionEntity();
        session.setRefImgName(ref);
        session.setDistImgName1(dist1);
        session.setDistImgName2(dist2);
        //sessionRepository.save(session);
        return "start";
    }*/
}
