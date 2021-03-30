package com.mmoi.javauniversity.controller;

import com.mmoi.javauniversity.models.Session;
import com.mmoi.javauniversity.models.SessionEntity;
import com.mmoi.javauniversity.repo.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class MainController  {
    /*TODO: paths from CL*/
    /*@Autowired
    private Environment env;
    {
        System.out.println("ENVVV"  + env.getProperty("property.dir"));
    }*/

    @Autowired
    private static ApplicationArguments applicationArguments;

    //private static String[] paths = applicationArguments.getSourceArgs();
    static ArrayList<String> ref_paths = new ArrayList<>();
    static ArrayList<String> dist_paths = new ArrayList<>();
    static List<String> dist_psnr = new ArrayList<>();
    static List<String> dist_ssim = new ArrayList<>();
    static File[] ref_tmp;
    static File[] dist_tmp;
    static String PSNR;
    static String SSIM;


    static {
        String path = System.getenv("userdir");

        if (path == null)
        {
            String cwd = System.getProperty("user.dir"); // get env
            System.out.println("Current working directory : " + cwd);
            ref_tmp = new File("/Users/artembarysev/Desktop/refImages").listFiles();
            dist_tmp = new File("/Users/artembarysev/Desktop/dbImages").listFiles();
            PSNR = "/Users/artembarysev/Desktop/dbMETR/psnr_base4.txt";
            SSIM = "/Users/artembarysev/Desktop/dbMETR/ssim_base4.txt";
        }
        else {
            ref_tmp = new File(path + "/refImages").listFiles();
            dist_tmp = new File(path + "/distImages").listFiles();
            PSNR = path + "/psnr.txt";
            SSIM = path + "/ssim.txt";
        }

        if (ref_tmp != null && (ref_tmp.length > 0)) {
            System.out.println("OK from ref");
            try (Stream<File> tmp_paths = Arrays.stream(ref_tmp).sequential()) {
                tmp_paths
                        .forEach(i -> ref_paths.add(i.getName()));
            }
        }

        if (dist_tmp != null && (dist_tmp.length > 0)) {
            System.out.println("OK from dist");

            try (Stream<File> tmp_paths = Arrays.stream(dist_tmp).sequential()) {
                tmp_paths
                        .forEach(i -> dist_paths.add(i.getName()));
            } catch (Exception e) {
                System.out.println(e.fillInStackTrace());
            }
        }
        try (BufferedReader br = Files.newBufferedReader(Paths.get(PSNR))) {
            dist_psnr = br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader br = Files.newBufferedReader(Paths.get(SSIM))) {
            dist_ssim = br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Autowired
    private SessionRepository sessionRepository;

    private SessionEntity sessionEntity;

    private Session session;

    @GetMapping("/")
    public String home(Model model, HttpServletResponse response) {
        //userCookie = user;
        Cookie c = new Cookie("user", "0");
        response.addCookie(c);
        model.addAttribute("title", "Main page");
        return "home";
    }

    @GetMapping("/home")
    public String home2(Model model) {
        model.addAttribute("title", "Main page");
        return "home";
    }

    @GetMapping("/start")
    public String GetStartSession(Model model) throws Exception {
        Iterable<Session> sessions = sessionRepository.findAll();
        model.addAttribute("sessions", sessions);
        model.addAttribute("title", "Start");
        return "start";
    }

    @PostMapping("/start")
    public String PostStartSession(@CookieValue(name = "user") String user, Model model) {
        session = new Session();
        sessionEntity = new SessionEntity();

        System.out.println(user);

        String ref, dist1, dist2;
        ArrayList<String> temp = generateImages();
        dist1 = temp.get(0);
        dist2 = temp.get(1);
        ref = temp.get(2);
        System.out.println("start    " + dist1 + " " + dist2);

        model.addAttribute("ref", "images/ref_images/" + ref);
        model.addAttribute("dist1", "images/dbImages/" + dist1);
        model.addAttribute("dist2", "images/dbImages/" + dist2);

        session.setUserCookie(user);
        sessionEntity.setUserCookie(session.getUserCookie());

        sessionEntity.setRefImgName(ref);
        sessionEntity.setDistImgName1(dist1);
        sessionEntity.setDistImgName2(dist2);
        sessionEntity.setStart(new Date());
        sessionEntity.setSession(session);
        sessionEntity.setInSessionId((long) session.getSessionSize());

        return "start";
    }

    @PostMapping("/nextimage")
    public String NextImage(@RequestParam String chosen, Model model) {

        sessionEntity.setStop(new Date());
        sessionEntity.setChosen(chosen);
        session.addSessionEntity(sessionEntity);
        sessionEntity.setInSessionId(session.getSessionCount());
        sessionRepository.save(session);

        sessionEntity = new SessionEntity();

        String ref, dist1, dist2;
        ArrayList<String> temp = generateImages();
        dist1 = temp.get(0);
        dist2 = temp.get(1);
        ref = temp.get(2);

        model.addAttribute("ref", "images/ref_images/" + ref);
        model.addAttribute("dist1", "images/dbImages/" + dist1);
        model.addAttribute("dist2", "images/dbImages/" + dist2);

        sessionEntity.setRefImgName(ref);
        sessionEntity.setDistImgName1(dist1);
        sessionEntity.setDistImgName2(dist2);
        sessionEntity.setStart(new Date());
        sessionEntity.setSession(session);
        sessionEntity.setUserCookie(session.getUserCookie());

        return "start";
    }

    @PostMapping("/stop")
    public String StopSession(Model model) {
        System.out.println("stop");
        model.addAttribute("title", "Main page");
        sessionEntity.setStop(new Date());
        sessionEntity.setChosen("0");
        session.addSessionEntity(sessionEntity);
        sessionEntity.setInSessionId(session.getSessionCount());
        sessionRepository.save(session);
        return "redirect:/";
    }

    @GetMapping("/stop")
    public String StopSessionWithEsc(Model model) {
        System.out.println("esc");
        model.addAttribute("title", "Main page");
        sessionEntity.setStop(new Date());
        sessionEntity.setChosen("0");
        session.addSessionEntity(sessionEntity);
        sessionEntity.setInSessionId(session.getSessionCount());
        sessionRepository.save(session);
        return "redirect:/";
    }

    private ArrayList<String> generateImages() {
        String ref = generateRefImage();
        String dist1 = generateDistImage(ref);
        String dist2 = generateDistImage(ref);
        ArrayList<String> res = new ArrayList<>(Arrays.asList(dist1, dist2, ref));
        if (!Compare(dist1, dist2) || (dist1.equals(dist2)))
            res = generateImages();
        return res;
    }

    private String generateRefImage() {
        Random rand = new Random();
        int index = rand.nextInt(ref_paths.size());
        //System.out.println("index_ref " + index + "  size " + ref_paths.size());
        return ref_paths.get(index);
    }

    private String generateDistImage(String ref) {
        Random rand = new Random();
        ArrayList<String> tmp = new ArrayList<>();

        dist_paths.stream().filter(i -> i.contains(ref.split("\\.")[0])).forEach(tmp::add);
        int index = rand.nextInt(tmp.size());
        return tmp.get(index);
    }

    private boolean Compare(String distOne, String distTwo) {
        float ssim1 = 0, ssim2 = 0, psnr1 = 0, psnr2 = 0;

        if (dist_ssim.isEmpty() || dist_psnr.isEmpty()) {
            //dist_psnr.forEach(System.out::println);
            return false;
        }
        Optional<String> dist1_psnr = dist_psnr.stream().filter(i -> (i.contains(distOne))).findFirst();
        Optional<String> dist2_psnr = dist_psnr.stream().filter(i -> (i.contains(distTwo))).findFirst();

        Optional<String> dist1_ssim = dist_ssim.stream().filter(i -> (i.contains(distOne))).findFirst();
        Optional<String> dist2_ssim = dist_ssim.stream().filter(i -> (i.contains(distTwo))).findFirst();

        if (dist1_psnr.isPresent() && dist2_psnr.isPresent()) {
            psnr1 = Float.parseFloat(dist1_psnr.get().split(" ")[1]);
            psnr2 = Float.parseFloat(dist2_psnr.get().split(" ")[1]);
        }
        if (dist1_ssim.isPresent() && dist2_ssim.isPresent()) {
            ssim1 = Float.parseFloat(dist1_ssim.get().split(" ")[1]);
            ssim2 = Float.parseFloat(dist2_ssim.get().split(" ")[1]);
        }
        return !(Math.abs(ssim1 - ssim2) > 0.3) && !(Math.abs(psnr1 - psnr2) > 3);
    }

}
