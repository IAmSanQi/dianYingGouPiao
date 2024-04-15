package com.example.dianyinggoupiao_springboot.server.impl;

import com.example.dianyinggoupiao_springboot.entity.Movie;
import com.example.dianyinggoupiao_springboot.entity.MovieAllInfo;
import com.example.dianyinggoupiao_springboot.entity.Movie_info;
import com.example.dianyinggoupiao_springboot.mapper.CinemaAndMovieMapper;
import com.example.dianyinggoupiao_springboot.mapper.MovieMapper;
import com.example.dianyinggoupiao_springboot.server.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    CinemaAndMovieMapper cinemaAndMovieMapper;

    /**
     * 日期格式化
     * @param time Timestamp类型数据
     * @return String
     */
    public String formatData(Timestamp time){
        String date="yyyy-MM-dd HH:mm";
        String data="";
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat(date);
        data=simpleDateFormat.format(time);
        return data;
    }

    /**
     * 按时间升序排序
     * @param list List<MovieAllInfo>
     * @return List<MovieAllInfo>
     */
    @Override
    public List<MovieAllInfo> TimeSort(List<MovieAllInfo> list){
        list.sort(new Comparator<MovieAllInfo>() {
            @Override
            public int compare(MovieAllInfo o1, MovieAllInfo o2) {
                return o2.getMovie_info().getTime().compareTo(o1.getMovie_info().getTime());
            }
        });
        return list;
    }
    /**
     * 按评分升序排序
     * @param list List<MovieAllInfo>
     * @return List<MovieAllInfo>
     */
    @Override
    public List<MovieAllInfo> ScoreSort(List<MovieAllInfo> list){
        list.sort(new Comparator<MovieAllInfo>() {
            @Override
            public int compare(MovieAllInfo o1, MovieAllInfo o2) {
                return o2.getScore().compareTo(o1.getScore());
            }
        });
        return list;
    }
    /**
     * 按票房升序排序
     * @param list List<MovieAllInfo>
     * @return List<MovieAllInfo>
     */
    @Override
    public List<MovieAllInfo> TicketSort(List<MovieAllInfo> list){
        list.sort(new Comparator<MovieAllInfo>() {
            @Override
            public int compare(MovieAllInfo o1, MovieAllInfo o2) {
                return o2.getTicketNumber().compareTo(o1.getTicketNumber());
            }
        });
        return list;
    }
    /**
     * 按想看的人数升序排序
     * @param list List<MovieAllInfo>
     * @return List<MovieAllInfo>
     */
    @Override
    public List<MovieAllInfo> SeeSort(List<MovieAllInfo> list){
        list.sort(new Comparator<MovieAllInfo>() {
            @Override
            public int compare(MovieAllInfo o1, MovieAllInfo o2) {
                return o2.getSee().compareTo(o1.getSee());
            }
        });
        return list;
    }
    /**
     * 按播放时长升序排序
     * @param list List<MovieAllInfo>
     * @return List<MovieAllInfo>
     */
    @Override
    public List<MovieAllInfo> PlayTimeSort(List<MovieAllInfo> list){
        list.sort(new Comparator<MovieAllInfo>() {
            @Override
            public int compare(MovieAllInfo o1, MovieAllInfo o2) {
                return o2.getMovie_info().getPlay_time().compareTo(o1.getMovie_info().getPlay_time());
            }
        });
        return list;
    }

    @Override
    public List<MovieAllInfo> findMovieAllInfoByNames(List<String> nameArr) {
        return movieMapper.findMovieAllInfoByNames(nameArr);
    }


    /**
     * 查找Movie表所有内容
     * @return List<Movie>
     */
    @Override
    public List<Movie> findMovieAll() {
        return movieMapper.findMovieAll();
    }

    @Override
    public Movie findById(int id) {
        return movieMapper.findById(id);
    }

    /**
     * 根据电影名称查询movie中的信息
     * @param name 电影名称
     * @return Movie
     */
    @Override
    public Movie findByName(String name) {
        return movieMapper.findByName(name);
    }

    /**
     * 判断是否在上映
     * @return List<Movie>
     */
    @Override
    public List<Movie> MovieIsBlockbuster() {
        List<Movie> list = movieMapper.findMovieAll();
        List<Movie> blockbuster = new ArrayList<>();
        for (Movie movie : list) {
            if (Objects.equals(movie.getIsBlockbuster(), "1")) {
                blockbuster.add(movie);
            }
        }
        return blockbuster;
    }

    /**
     * 查找已经上映并且返回num个票房最高的电影数据
     * @return 返回电影数据
     */
    @Override
    public List<Movie> MovieTicket(int num) {
        List<Movie> list = MovieIsBlockbuster();
        List<Movie> blockbuster = new ArrayList<>();
        Collections.sort(list, new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                return Integer.parseInt(o2.getTicketNumber()) - Integer.parseInt(o1.getTicketNumber());
            }
        });
        for (int i = 0; i < num; i++) {
            blockbuster.add(list.get(i));
        }

        return blockbuster;
    }


    /**
     * 查找没有上映并且返回十个想看最多的电影数据
     * @return 返回电影数据
     */
    @Override
    public List<Movie> MovieSee(int num) {
        List<Movie> list = movieMapper.findMovieAll();
        List<Movie> Release = new ArrayList<>();
        List<Movie> see = new ArrayList<>();
        for (Movie movie : list) {
            if (Objects.equals(movie.getIsRelease(), "1")) {
                Release.add(movie);
            }
        }
        Collections.sort(Release, new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                return Integer.parseInt(o2.getSee()) - Integer.parseInt(o1.getSee());
            }
        });
        for (int i = 0; i < num; i++) {
            see.add(Release.get(i));
        }
        return see;
    }

    /**
     * 查找没有上映并且返回十个评分最高的电影数据
     * @return 返回电影数据
     */
    @Override
    public List<Movie> MovieScore(int num) {
        List<Movie> list = movieMapper.findMovieAll();
        List<Movie> score = new ArrayList<>();

        Collections.sort(list, new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                return (int)((Double.parseDouble(o2.getScore()) - Double.parseDouble(o1.getScore()))*10);
            }
        });
        for (int i = 0; i < num; i++) {
            score.add(list.get(i));
        }
        return score;
    }

    /**
     * 获取所有对应的电影信息
     * @param movieName 电影的名称
     * @return List<Movie>
     */
    @Override
    public List<Movie> findMovieNameSomeEqual(String movieName) {
        List<Movie> list=movieMapper.findMovieAll();
        List<Movie> data=new ArrayList<>();
        for (Movie item: list) {
            if(item.getName() != null&&item.getName().contains(movieName)) {
                data.add(item);
            }
        }
        return data;
    }

    /**
     * 获取所有Movie_info信息
     * @return List<Movie_info>
     */
    @Override
    public List<Movie_info> findMovieInfoAll() {
        return movieMapper.findMovieInfoAll();
    }

    /**
     * 获取所有Movie_info和movie表信息
     * @return List<MovieAllInfo>
     */
    @Override
    public List<MovieAllInfo> findMovieAllInfo() {
        return movieMapper.findMovieAllInfo();
    }



    /**
     * 按名字查找表Movie_info和movie对应信息
     * @param movieName 电影名字
     * @return MovieAllInfo
     */
    @Override
    public MovieAllInfo findMovieAllInfoByName(String movieName) {
        List<MovieAllInfo>  list=movieMapper.findMovieAllInfo();
        MovieAllInfo movieAllInfo = null;
        for (MovieAllInfo m:list) {
            if (m.getName().equals(movieName)){
                movieAllInfo=m;
            }
        }
        return movieAllInfo;
    }

    /**
     * 按字符串查找表Movie_info和movie对应信息
     * @param Str 字符串
     * @return List<MovieAllInfo>
     */
    @Override
    public List<MovieAllInfo> findByMovieNameInfo(String Str) {
        List<MovieAllInfo> list=movieMapper.findMovieAllInfo();
        List<MovieAllInfo> dataInfo = new ArrayList<>();
        if (Str!=null&&Str.length()>0){
            for (MovieAllInfo movieAllInfo : list) {
                if (movieAllInfo.getName().contains(Str)){
                    dataInfo.add(movieAllInfo);
                }
            }
            return dataInfo;
        }
        return null;
    }

    /**
     * 按照不同的要求来查找对应的电影信息
     * @param type 电影的类型
     * @param location 电影的地区
     * @param year 电影的年份
     * @param radio 排序方式 1:按热门排序 2:按时间排序 3:按评分排序
     * @param index 按不同方式筛选 1:正在热映 2:即将上映 3:经典影片
     * @return List<MovieAllInfo>
     */
    @Override
    public List<MovieAllInfo> findByTypeInfo(String type,String location,String year,String radio,String index) {
        List<MovieAllInfo> movieInfo=movieMapper.findMovieAllInfo();
        List<MovieAllInfo> list=new ArrayList<>();
        //根据index 来判断是否是在上映
        switch (index){
            case "1":
                for (MovieAllInfo m:movieInfo) {
                    if (m.getIsBlockbuster().equals("1")){
                        list.add(m);
                    }
                }
                break;
            case "2":
                for (MovieAllInfo m:movieInfo) {
                    if (m.getIsRelease().equals("1")){
                        list.add(m);
                    }
                }
                break;
            case "3":
                list.addAll(movieInfo);
                break;
        }
        //根据前端数据来筛选合适的数据
        List<MovieAllInfo> Data= new ArrayList<>();
        if (!type.equals("全部")){
            if (!location.equals("全部")){
                if(!year.equals("全部")){
                    //根据类型和地区以及年份一起查找对应的电影信息
                    for (MovieAllInfo m:list) {
                        //把日期格式 并转为字符串
                        String time=formatData(m.getMovie_info().getTime());
                        if (m.getMovie_info().getType().contains(type)){
                            if (m.getMovie_info().getLocation().contains(location)){
                                if (time.contains(year)){
                                    Data.add(m);
                                }
                            }
                        }
                    }
                }else {
                    //根据类型和地区查找对应的电影信息
                    for (MovieAllInfo m:list) {
                        if (m.getMovie_info().getType().contains(type)){
                            if (m.getMovie_info().getLocation().contains(location)){
                                Data.add(m);
                            }
                        }
                    }
                }
            }else if (!year.equals("全部")){
                //根据类型和年份查找对应的电影信息
                for (MovieAllInfo m:list) {
                    //把日期格式 并转为字符串
                    String time=formatData(m.getMovie_info().getTime());
                    if (m.getMovie_info().getType().contains(type)){
                        if (time.contains(year)){
                            Data.add(m);
                        }
                    }
                }
            }else {
                //根据类型查找对应的电影信息
                for (MovieAllInfo m:list) {
                    if (m.getMovie_info().getType().contains(type)){
                        Data.add(m);
                    }
                }
            }
        }else if (!location.equals("全部")){
            if (!year.equals("全部")){
                //根据地区和年份查找对应的电影信息
                for (MovieAllInfo m:list) {
                    //把日期格式 并转为字符串
                    String time=formatData(m.getMovie_info().getTime());
                    if (m.getMovie_info().getLocation().contains(location)){
                        if (time.contains(year)){
                            Data.add(m);
                        }
                    }
                }
            }else {
                //根据地区查找对应的电影信息
                for (MovieAllInfo m:list) {
                    if (m.getMovie_info().getLocation().contains(location)){
                        Data.add(m);
                    }
                }
            }
        }else if (!year.equals("全部")){
            //根据年份查找对应的电影信息
            for (MovieAllInfo m:list) {
                //把日期格式 并转为字符串
                String time=formatData(m.getMovie_info().getTime());
                if (time.contains(year)){
                    Data.add(m);
                }
            }
        }else {
            Data.addAll(list);
        }
        switch (radio) {
            case "1":
                Data.sort(new Comparator<MovieAllInfo>() {
                    @Override
                    public int compare(MovieAllInfo o1, MovieAllInfo o2) {
                        return o2.getTicketNumber().compareTo(o1.getTicketNumber());
                    }
                });
                break;
            case "2":
                Data.sort(new Comparator<MovieAllInfo>() {
                    @Override
                    public int compare(MovieAllInfo o1, MovieAllInfo o2) {
                        return o2.getMovie_info().getTime().compareTo(o1.getMovie_info().getTime());
                    }
                });
                break;
            case "3":
                Data.sort(new Comparator<MovieAllInfo>() {
                    @Override
                    public int compare(MovieAllInfo o1, MovieAllInfo o2) {
                        return o2.getScore().compareTo(o1.getScore());
                    }
                });
                break;
        }
        return Data;
    }

    /**
     * 返回在上映的影片信息
     * @return List<MovieAllInfo>
     */
    @Override
    public List<MovieAllInfo> MovieAllInfoBlockbuster(){
        List<MovieAllInfo> list=movieMapper.findMovieAllInfo();
        List<MovieAllInfo> data = new ArrayList<>();
        for (MovieAllInfo m: list) {
            if (m.getIsBlockbuster().equals("1")){
                data.add(m);
            }
        }
        return data;
    }

    /**
     * 返回不在上映的影片信息
     * @return List<MovieAllInfo>
     */
    @Override
    public List<MovieAllInfo> MovieAllInfoRelease(){
        List<MovieAllInfo> list=movieMapper.findMovieAllInfo();
        List<MovieAllInfo> data = new ArrayList<>();
        for (MovieAllInfo m: list) {
            if (m.getIsRelease().equals("1")){
                data.add(m);
            }
        }
        return data;
    }

    @Override
    public int countMovieBlockbuster() {
        List<MovieAllInfo> list=movieMapper.findMovieAllInfo();
        int count=0;
        for (MovieAllInfo m:list) {
            if (m.getIsBlockbuster().equals("1")){
                count++;
            }
        }
        return count;
    }

    @Override
    public int countMovieRelease() {
        List<MovieAllInfo> list=movieMapper.findMovieAllInfo();
        int count=0;
        for (MovieAllInfo m:list) {
            if (m.getIsRelease().equals("1")){
                count++;
            }
        }
        return count;
    }

    @Override
    public void addMovie(Movie movie) {
        movieMapper.addMovie(movie);
    }

    @Override
    public void addMovieInfo(Movie_info movie_info) {
        movieMapper.addMovieInfo(movie_info);
    }

    @Override
    public void updateMovieInfo(Movie_info movie_info) {
        movieMapper.updateMovieInfo(movie_info);
    }

    /**
     * 删除movie
     * @param movie_id
     * @param movieName
     */
    @Override
    public void delMovie(int movie_id, String movieName) {
        try {
            cinemaAndMovieMapper.delByMovie_id(movie_id);
            movieMapper.delMovieByName(movieName);
            movieMapper.delMovieInfoByName(movieName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
