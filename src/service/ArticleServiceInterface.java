/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;
import entity.Article;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author esprit
 */
public interface ArticleServiceInterface {
    public void addArticle(Article article, List<File> images);
    public void updateArticle(Article article);
    public void deleteArticle(Article article);
    public void deleteArticle(int id);
    public ArrayList<Article> findAll();
    public Article findById(int id);
    public ArrayList<Article> findBy(Map<String, String> critere);
    public ArrayList<Article> getBoosted();
    public ArrayList<Article> getArticlesByOwner(int id);
    public ArrayList<Article> getArticlesByLover(int id);
    public ArrayList<Article> getArticleByAcheteur(int id);
}
