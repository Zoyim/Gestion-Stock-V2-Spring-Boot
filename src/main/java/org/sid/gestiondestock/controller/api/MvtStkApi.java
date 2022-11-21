package org.sid.gestiondestock.controller.api;

import io.swagger.annotations.Api;
import org.sid.gestiondestock.dto.MvtStkDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

import static org.sid.gestiondestock.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/mvtstk")
public interface MvtStkApi {

    @GetMapping(APP_ROOT + "/mvtstk/stockreel/{idArticle}")
    BigDecimal stockReelArticle(@PathVariable("idArticle") Integer idArticle);

    @GetMapping(APP_ROOT + "/mvtstk/filter/article/{idArticle}")
    List<MvtStkDto> mvtStkArticle(@PathVariable("idArticle") Integer idArticle);

    @PostMapping(APP_ROOT + "/mvtstk/entree")
    MvtStkDto entreeStock(@RequestBody MvtStkDto dto);

    @PostMapping(APP_ROOT + "/mvtstk/sortie")
    MvtStkDto sortieStock(@RequestBody MvtStkDto dto);

    @PostMapping(APP_ROOT + "/mvtstk/correctionpos")
    MvtStkDto correctionStockPos(@RequestBody MvtStkDto dto);

    @PostMapping(APP_ROOT + "/mvtstk/correctionneg")
    MvtStkDto correctionStockNeg(@RequestBody MvtStkDto dto);
}
