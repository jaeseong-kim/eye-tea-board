package com.eyeteaboard.eyeteaboard.dto;


import lombok.Getter;

@Getter
public class PageInfoDto {

  private int currnetPage;

  private int startPage;

  private int endPage;

  private boolean hasPrevious;
  private boolean hasNext;

  public PageInfoDto(int currnetPage, int startPage, int endPage, boolean hasPrevious,
      boolean hasNext) {
    this.currnetPage = currnetPage;
    this.startPage = startPage;
    this.endPage = endPage;
    this.hasPrevious = hasPrevious;
    this.hasNext = hasNext;
  }
}
