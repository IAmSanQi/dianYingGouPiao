package com.example.dianyinggoupiao_springboot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.dianyinggoupiao_springboot.entity.Cinema;
import com.example.dianyinggoupiao_springboot.entity.Movie;
import com.example.dianyinggoupiao_springboot.entity.MovieAllInfo;
import com.example.dianyinggoupiao_springboot.entity.Movie_info;
import com.example.dianyinggoupiao_springboot.mapper.CinemaAndMovieMapper;
import com.example.dianyinggoupiao_springboot.mapper.MovieMapper;
import com.example.dianyinggoupiao_springboot.repositroy.CinemaRepository;
import com.example.dianyinggoupiao_springboot.server.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/Movie")
public class MovieController {


    @Autowired
    private MovieService movieService;
    @Autowired
    private ImageUploadService imageUploadService;
    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private CinemaAndMovieService cinemaAndMovieService;

    @Autowired
    private CinemaAndMovieMapper cinemaAndMovieMapper;

    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private CinemaAccService cinemaAccService;


    /**
     * 查找并格式化数据
     *
     * @return 返回固定格式全部数据
     */
    public List<Movie> initMovie(List<Movie> list){
        List<Movie> newList=new ArrayList<>();
        String see="";
        String TicketNumber="";
        for (Movie movie:list) {
            if (Objects.equals(movie.getIsBlockbuster(), "1")){
                movie.setIsBlockbuster("是");
                movie.setIsRelease("否");
            }else{
                movie.setIsBlockbuster("否");
                movie.setIsRelease("是");
            }
            see=String.valueOf(Integer.parseInt(movie.getSee())/10000
                    +"."
                    +String.valueOf(Integer.parseInt(movie.getSee())/1000).substring(0,1)
                    +"万");
            movie.setSee(see);
            TicketNumber=String.valueOf(Integer.parseInt(movie.getTicketNumber())/10000
                    +"."
                    +String.valueOf(Integer.parseInt(movie.getTicketNumber())/1000).substring(0,1)
                    +"万");
            movie.setScore(movie.getScore());
            movie.setTicketNumber(TicketNumber);
            newList.add(movie);
        }
        return newList;
    }

    /**
     * 日期格式化
     * @param rawDateStr 原始日期字符串
     * @return yyyy-MM-dd HH:mm:ss 日期格式
     */
    public Timestamp initDate(String rawDateStr){

        // 定义原始日期格式，符合ISO 8601格式
        SimpleDateFormat rawDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        rawDateFormat.setTimeZone(TimeZone.getTimeZone("UTC")); // 设置时区为UTC

        try {
            // 将原始日期字符串解析为 Date 对象
            Date date = rawDateFormat.parse(rawDateStr);

            // 将 Date 对象转换为 Timestamp 对象
            return new Timestamp(date.getTime());
        } catch (ParseException e) {
            System.out.println("日期解析失败: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/findMovieAll")
    public List<Movie> findMovieAll() {
        return initMovie(movieService.findMovieAll());
    }
    @GetMapping("/MovieIsBlockbuster")
    public List<Movie> MovieIsBlockbuster() {
        return initMovie(movieService.MovieIsBlockbuster());
    }
    @GetMapping("/MovieTicketEight")
    public List<Movie> MovieTicketEight(){
        return initMovie(movieService.MovieTicket(8));
    }
    @GetMapping("/MovieTicketFive")
    public List<Movie> MovieTicketFive(){
        return initMovie(movieService.MovieTicket(5));
    }
    @GetMapping("/MovieSeeTen")
    public List<Movie> MovieSeeTen(){
        return initMovie(movieService.MovieSee(10));
    }
    @GetMapping("/MovieSeeEight")
    public List<Movie> MovieSeeEight(){
        return initMovie(movieService.MovieSee(8));
    }
    @GetMapping("/MovieScoreTen")
    public List<Movie> MovieScoreTen(){
        return initMovie(movieService.MovieScore(10));
    }
    @GetMapping("/MovieScoreSix")
    public List<Movie> MovieScoreSix(){
        return initMovie(movieService.MovieScore(6));
    }
    @PostMapping("/findMovieNameSomeEqual")
    public List<Movie> findMovieNameSomeEqual(@RequestBody Map<String, Object> movieName){
        String name=(String)movieName.get("movieName");
        if (name==null||name.length()==0){
            return null;
        }
        return movieService.findMovieNameSomeEqual(name);
    }


    /**
     * 数据格式化
     * @param info MovieAllInfo数据
     * @return MovieAllInfo
     */
    public MovieAllInfo initMovieAllInfo(MovieAllInfo info){
        String Ticket="";
        String see="";
        if (info!=null) {
            //票格式
            Ticket = String.valueOf(Integer.parseInt(info.getTicketNumber()) / 10000
                    + "."
                    + String.valueOf(Integer.parseInt(info.getTicketNumber()) / 1000).substring(0, 1)
                    + "万");
            info.setTicketNumber(Ticket);

//            //想看格式
//            see = String.valueOf(Integer.parseInt(info.getSee()) / 10000
//                    + "."
//                    + String.valueOf(Integer.parseInt(info.getSee()) / 1000).substring(0, 1)
//                    + "万");
//            info.setSee(see);


            //日期格式
            Timestamp timestamp;
            String DateInit = "yyyy-MM-dd HH:mm";
            SimpleDateFormat sdf = new SimpleDateFormat(DateInit);
            String formattedTime = sdf.format(info.getMovie_info().getTime());
            try {
                timestamp = new Timestamp(sdf.parse(formattedTime).getTime());

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            info.getMovie_info().setTime(timestamp);
            info.getMovie_info().setTimeString(formattedTime);

            //电影类型格式
            String type = info.getMovie_info().getType();
            type = type.replace(",", " ");
            info.getMovie_info().setType(type);
        }

        return info;
    }

    /**
     * 多条数据格式化
     * @param list List<MovieAllInfo>
     * @return List<MovieAllInfo>
     */
    public List<MovieAllInfo> initMovieAllInfoList(List<MovieAllInfo> list){
        List<MovieAllInfo> data=new ArrayList<>();
        for (MovieAllInfo m:list) {
            data.add(initMovieAllInfo(m));
        }
        return data;
    }
    @GetMapping("/findMovieInfoAll")
    public List<Movie_info> findMovieInfoAll() {
        return movieService.findMovieInfoAll();
    }
    @GetMapping("/findMovieAllInfo")
    public List<MovieAllInfo> findMovieAllInfo() {
        return movieService.findMovieAllInfo();
    }
    @PostMapping("/findMovieAllInfoByName")
    public MovieAllInfo findMovieAllInfoByName(@RequestBody Map<String, String> requestBody) {
        String movieName=requestBody.get("movieName");
        return initMovieAllInfo(movieService.findMovieAllInfoByName(movieName));
    }

    @PostMapping("/findByMovieNameInfo")
    public List<MovieAllInfo> findByMovieNameInfo(@RequestBody Map<String,String> nameInfo) {
        String name=nameInfo.get("movieName");
        List<MovieAllInfo> list=movieService.findByMovieNameInfo(name);
        List<MovieAllInfo> data=new ArrayList<>();
        if (list!=null&&list.size()>0){
            data.addAll(list);
        }
        return initMovieAllInfoList(data);
    }

    @PostMapping("/findByConditionInfo")
    public List<MovieAllInfo> findByTypeInfo(@RequestBody Map<String,String> request){
        String data=request.get("data");
        JSONObject jsonObject = JSON.parseObject(data);
        String type=jsonObject.getString("type");
        String location=jsonObject.getString("location");
        String year=jsonObject.getString("year");
        String radio=jsonObject.getString("radio");
        String index=jsonObject.getString("index");
        return movieService.findByTypeInfo(type,location,year,radio,index);
    }

    @GetMapping ("/getBlockbusterLen")
    public int countMovieBlockbuster(){
        return movieService.countMovieBlockbuster();
    }
    @GetMapping("/getReleaseLen")
    public int countMovieRelease(){
        return movieService.countMovieRelease();
    }

    @GetMapping("/findBlockbusterInfo")
    public List<MovieAllInfo> findBlockbusterInfo() {
        return initMovieAllInfoList(movieService.ScoreSort(movieService.MovieAllInfoBlockbuster()));
    }

    @GetMapping("/findReleaseInfo")
    public List<MovieAllInfo> findReleaseInfo() {
        return initMovieAllInfoList((movieService.SeeSort(movieService.MovieAllInfoRelease())));
    }

    @GetMapping("/TicketSort")
    public List<MovieAllInfo> TicketSort(){
        return initMovieAllInfoList(movieService.TicketSort(findMovieAllInfo()));
    }

    @GetMapping("/ScoreSort")
    public List<MovieAllInfo> ScoreSort(){
        return initMovieAllInfoList(movieService.ScoreSort(findMovieAllInfo()));
    }

    /**
     * 新增一条电影信息并和相应的影院建立联系
     * @param Image 图片
     * @param objectData 电影信息
     * @param cinemaAccName 影院账户名称
     * @return Boolean
     */
    @PostMapping("/addMovie")
    public Boolean addMovieAndMovie_info(
            @RequestParam(name = "image") MultipartFile Image,
            @RequestParam(name = "movieInfo") String objectData,
            @RequestParam(name = "cinemaAccName") String cinemaAccName
    ){

        Movie movie=new Movie();
        Movie_info movie_info=new Movie_info();
        System.out.println(objectData);
        JSONObject jsonObject = JSON.parseObject(objectData);
        String name = jsonObject.getString("name");
        String introduce = jsonObject.getString("introduce");
        String starring = jsonObject.getString("starring");
        String location = jsonObject.getString("location");
        String time = jsonObject.getString("time");
        String type = jsonObject.getString("type");
        String play_time = jsonObject.getString("play_time");
        try {
            //获取影院的id
            Optional<Cinema> cinema1 = cinemaService.findById(cinemaAccService.findCinemaId(cinemaAccName.replace("\"","")));
            assert cinema1.orElse(null) != null;
            int cinema_id=cinema1.orElse(null).getCinemaId();

            List<Movie> movieAll = movieService.findMovieAll();
            //---------------------------如果电影名称在电影片库里存在--------------------------------
            for (Movie movie1 : movieAll) {
                //判断是否存在
                if (Objects.equals(movie1.getName(), name)){
                    System.out.println("电影名称在电影片库里存在");
                    //获取影院中的所有电影
                    List<MovieAllInfo> cinemaAllMovieInfo = cinemaAndMovieMapper.findCinemaAllMovieInfo(cinema_id);

                    //判断影院中是否已经有了该电影
                    for (MovieAllInfo movieAllInfo : cinemaAllMovieInfo) {
                        if (movieAllInfo.getMovie_info().getName().equals(name)){
                            System.out.println("影院中已经有了该电影");
                            return false;
                        }
                    }
                    //如果影院中不存在就添加关系
                    cinemaAndMovieService.addCinemaAndMovie(cinema_id,movie1.getId());
                    System.out.println("影院中不存在添加关系成功");
                    return true;
                }
            }

            //---------------------------如果电影名称在电影片库里不存在--------------------------------
            //设置movie
            movie.setName(name);
            movie.setTicketNumber("0");
            movie.setScore("0");
            movie.setSee("0");
            movie.setIsBlockbuster("0");
            movie.setIsRelease("1");
            movieService.addMovie(movie);

            System.out.println("movie插入成功");
            //设置movie_info
            movie_info.setName(name);
            movie_info.setType(type);

            // 将 java.util.Date 转换为 java.sql.Timestamp
            Timestamp customTimestamp = initDate(time);

            movie_info.setTime(customTimestamp);
            movie_info.setPlay_time(play_time);
            movie_info.setLocation(location);
            movie_info.setIntroduce(introduce);
            movie_info.setStarring(starring);
            movieService.addMovieInfo(movie_info);

            System.out.println("movie_info 插入成功");

            //获取电影的id
            Integer movie_id = movieService.findByName(name).getId();
            //给movie和Cinema添加关系
            cinemaAndMovieService.addCinemaAndMovie(cinema_id,movie_id);
            System.out.println("movie和Cinema关系 插入成功");


            //插入图片信息
            ImageUploadController imageUploadController = new ImageUploadController();

            System.out.println(imageUploadController.saveMovieImage(Image, movie_id,movieMapper,imageUploadService));
            return true;
        }catch (Exception e){
            System.out.println("插入出错");
            System.out.println(e.getMessage());
            return false;
        }

    }

    /**
     * 新增一条电影信息
     * @param Image 图片
     * @param objectData 电影信息
     * @return Boolean
     */
    @PostMapping("addOneMovie")
    public Boolean addOneMovie(
            @RequestParam(name = "image") MultipartFile Image,
            @RequestParam(name = "movieInfo") String objectData
    ){
        System.out.println("movieInfo:"+objectData);
        JSONObject jsonObject = JSON.parseObject(objectData);
        String name = jsonObject.getString("name");
        String introduce = jsonObject.getString("introduce");
        String starring = jsonObject.getString("starring");
        String location = jsonObject.getString("location");
        String time = jsonObject.getString("time");
        String type = jsonObject.getString("type");
        String play_time = jsonObject.getString("play_time");


        //---------------------------如果电影名称在电影片库里存在--------------------------------
        List<Movie> movieAll = movieService.findMovieAll();
        for (Movie movie1 : movieAll) {
            //判断是否存在
            if (Objects.equals(movie1.getName(), name)){
                System.out.println("电影名称在电影片库里存在");
                return false;
            }
        }

        //---------------------------如果电影名称在电影片库里不存在--------------------------------
        //设置movie
        Movie movie=new Movie(name,"0","0","0","0","1");
        movieService.addMovie(movie);
        System.out.println("movie插入成功");

        // 将 java.util.Date 转换为 java.sql.Timestamp
        Timestamp customTimestamp = initDate(time);
        //设置movie_info
        Movie_info movie_info=new Movie_info(name,type,customTimestamp,play_time,location,introduce,starring);
        movieService.addMovieInfo(movie_info);
        System.out.println("movie_info 插入成功");

        //获取电影的id
        Integer movie_id = movieService.findByName(name).getId();
        //插入图片信息
        ImageUploadController imageUploadController = new ImageUploadController();
        //保存电影图片信息
        System.out.println(imageUploadController.saveMovieImage(Image, movie_id,movieMapper,imageUploadService));
        return true;
    }

    /**
     * 修改 movie_info
     * @param request movie_info
     * @return Boolean
     */
    @PostMapping("/updateMovie_info")
    public Boolean updateMovieInfo(@RequestBody Map<String ,String> request) {
        Movie_info movie_info=new Movie_info();
        try{
            String data=request.get("data");
            JSONObject jsonObject = JSON.parseObject(data);
            movie_info.setName(jsonObject.getString("name"));
            movie_info.setPlay_time(jsonObject.getString("play_time"));
            movie_info.setLocation(jsonObject.getString("location"));
            movie_info.setIntroduce(jsonObject.getString("introduce"));
            movie_info.setStarring(jsonObject.getString("starring"));
            movie_info.setType(jsonObject.getString("type"));
            movieService.updateMovieInfo(movie_info);
            return true;
        }catch (Exception e){
            System.out.println("修改失败");
        }
        return false;
    }

    /**
     * 修改 movie_info 包括图片
     * @param image 图片
     * @param objData movie_info
     * @return Boolean
     */
    @PostMapping("/updateMovie_infoAndImage")
    public Boolean updateMovie_infoAndImage(
            @RequestParam("image") MultipartFile image,
            @RequestParam("movieInfo") String objData
    ) {
        Movie_info movie_info=new Movie_info();
        try{

            JSONObject jsonObject = JSON.parseObject(objData);
            String name = jsonObject.getString("name");
            movie_info.setName(name);
            movie_info.setPlay_time(jsonObject.getString("play_time"));
            movie_info.setLocation(jsonObject.getString("location"));
            movie_info.setIntroduce(jsonObject.getString("introduce"));
            movie_info.setStarring(jsonObject.getString("starring"));
            movie_info.setType(jsonObject.getString("type"));
            movieService.updateMovieInfo(movie_info);

            //获取电影的id
            Integer movie_id = movieService.findByName(name).getId();
            //插入图片信息
            ImageUploadController imageUploadController = new ImageUploadController();
            //保存电影图片信息
            imageUploadController.saveMovieImage(image, movie_id,movieMapper,imageUploadService);
            return true;
        }catch (Exception e){
            System.out.println("修改失败");
        }
        return false;
    }

    /**
     * 查找movie中 name
     * @return movieNames（movie中所有电影的的name）
     */
    @GetMapping ("/findMovieNames")
    public List<String> findMovieNames(){
        List<String> movieNames = new ArrayList<>();
        List<Movie> movieAll = movieService.findMovieAll();
        for (Movie movie : movieAll) {
            movieNames.add(movie.getName());
        }
        return movieNames;
    }

    /**
     * 根据影院id查询影院中所有电影的name
     * @param cinema_id 影院id
     * @return 所有电影的name
     */
    @PostMapping("findMovieAllInfoByCinema_name")
    public List<MovieAllInfo> findMovieAllInfoByCinema_name(@RequestParam(name = "cinema_id") int cinema_id ){
        List<String> movieNames = cinemaAccService.findMovieById(cinema_id);
        return movieService.findMovieAllInfoByNames(movieNames);
    }

    @PostMapping("delMovie")
    public Boolean delMovie(@RequestParam("id") int id,@RequestParam("movieName")String movieName){
        System.out.println("id:"+id);
        System.out.println("movieName:"+movieName);
        try {
            //删除电影的后台保存图片，
            //--
            //删除电影的图片信息，
            //--
            //删除电影和电影信息以及和电影有关的影院 关联信息
            movieService.delMovie(id,movieName);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
