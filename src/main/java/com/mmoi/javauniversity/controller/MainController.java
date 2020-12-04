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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class MainController {

    static ArrayList<String> ref_paths = new ArrayList<>();
    static ArrayList<String> dist_paths = new ArrayList<>();
    static List<String> dist_psnr = new ArrayList<>();
    static List<String> dist_ssim = new ArrayList<>();

    static {
        String cwd = System.getProperty("user.dir");
        System.out.println("Current working directory : " + cwd);
        File[] ref_tmp = new File("src/main/resources/static/images/ref_images").listFiles();
        File[] dist_tmp = new File("src/main/resources/static/images/databaseImage").listFiles();
        //InSessionsId = new HashMap<>();
        if (ref_tmp!= null && (ref_tmp.length > 0)) {
            try (Stream<File> tmp_paths = Arrays.stream(ref_tmp).sequential()) {
                tmp_paths
                        .forEach(i -> ref_paths.add(i.getName()));
            }
        }

        if (dist_tmp != null && (dist_tmp.length > 0)) {
            try (Stream<File> tmp_paths = Arrays.stream(dist_tmp).sequential()) {
                tmp_paths
                        .forEach(i -> dist_paths.add(i.getName()));
            } catch (Exception e) {
                System.out.println(e.fillInStackTrace());
            }
        }

        String PSNR = "psnr_base4.txt";
        try (BufferedReader br = Files.newBufferedReader(Paths.get(PSNR))) {
            dist_psnr = br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String SSIM = "ssim_base4.txt";
        try (BufferedReader br = Files.newBufferedReader(Paths.get(SSIM))) {
            dist_ssim = br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private SessionEntityRepository sessionRepository;

    private SessionEntity sessionEntity;

    private Session session;

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
        sessionEntity = new SessionEntity();
        ArrayList<SessionEntity> tmp = new ArrayList<>();
        tmp.add(sessionEntity);
        session.setSessions(tmp);

        sessionEntity.setInSessionId((long) session.getSessionSize());
        //sessionEntity.setInSessionId(InSessionsId.get(session.getId()));

        String ref, dist1, dist2;
        ArrayList<String> temp = generateImages();
        dist1 = temp.get(0);
        dist2 = temp.get(1);
        ref = temp.get(2);
        System.out.println("start    " + dist1 + " " + dist2);

        model.addAttribute("ref", "/images/ref_images/" + ref);
        model.addAttribute("dist1", "/images/databaseImage/" + dist1);
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

        //System.out.println(chosen);

        sessionEntity = new SessionEntity();

        //System.out.println("session size" + session.getSessionSize());

        String ref, dist1, dist2;
        ArrayList<String> temp = generateImages();
        dist1 = temp.get(0);
        dist2 = temp.get(1);
        ref = temp.get(2);
        System.out.println("newImage    " + dist1 + " " + dist2);

        model.addAttribute("ref", "/images/ref_images/" + ref);
        model.addAttribute("dist1", "/images/databaseImage/" + dist1);
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

    @PostMapping("/stop")
    public String StopSession(Model model) {
        System.out.println("stop");
        model.addAttribute("title", "Main page");
        sessionEntity.setStop(new Date());
        sessionRepository.save(session);
        return "redirect:/";
    }

    @GetMapping("/stop")
    public String StopSessionWithEsc(Model model) {
        System.out.println("esc");
        model.addAttribute("title", "Main page");
        sessionEntity.setStop(new Date());
        sessionRepository.save(session);
        return "redirect:/";
    }

    private ArrayList<String> generateImages() {
        String ref = generateRefImage();
        String dist1 = generateDistImage(ref);
        String dist2 = generateDistImage(ref);
        ArrayList<String> res = new ArrayList<>(Arrays.asList(dist1, dist2, ref));
        if (!Compare(dist1, dist2))
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

        //System.out.println(ref.split("\\.")[0]);
        dist_paths.stream().filter(i -> i.contains(ref.split("\\.")[0])).forEach(tmp::add);
        int index = rand.nextInt(tmp.size());
        //System.out.println("index_dist  " + index + "  size " + tmp.size());
        return tmp.get(index);
    }

    private boolean Compare(String distOne, String distTwo) {
        float ssim1 = 0, ssim2 = 0, psnr1 = 0, psnr2 = 0;

        if (dist_ssim.isEmpty() || dist_psnr.isEmpty())
            return false;
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
        return !(Math.abs(ssim1 - ssim2) > 0.3) && !(Math.abs(psnr1 - psnr2) > 1);
    }

}
