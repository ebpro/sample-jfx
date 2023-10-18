package fr.univtln.bruno.samples.jfx.fxapp2.model;

import lombok.Data;

import java.util.List;

public record Page<E>(long dataSize,int pageSize,int pageNumber,List<E> content) {}
