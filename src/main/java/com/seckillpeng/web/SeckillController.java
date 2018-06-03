package com.seckillpeng.web;
import com.seckillpeng.dto.Exposer;
import com.seckillpeng.dto.SeckillExecution;
import com.seckillpeng.dto.SeckillResult;
import com.seckillpeng.entity.SeckillGoods;
import com.seckillpeng.enums.SeckillStatuEnums;
import com.seckillpeng.exception.RepeatKillException;
import com.seckillpeng.exception.SeckillCloseException;
import com.seckillpeng.server.SeckillGoodsServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
@Controller
@RequestMapping("/seckill")//url:模块/资源/{}/细分
public class SeckillController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillGoodsServer seckillGoodsServer;
    /**
     * 查询秒杀商品列表，返回给用户界面
     * @param model
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model model){
      List<SeckillGoods> seckillGoodsList =  seckillGoodsServer.findAllSeckillGoods();
      model.addAttribute("list",seckillGoodsList);
      return "list";
    }
    /**
     * 获取单个商品的库存记录
     * @param seckillId
     * @param model
     * @return
     */
    @RequestMapping(value = "{seckillId}/detail",method = RequestMethod.GET)
    public String detail (@PathVariable("seckillId") Long seckillId, Model model){
       if(seckillId == null){
           return "redirect:/seckill/list";
       }
      SeckillGoods seckillGoods =  seckillGoodsServer.findSeckillGoodsById(seckillId);
      if(seckillGoods == null){
          return "forward:/seckill/list";
      }
      model.addAttribute("seckillGoods",seckillGoods);
      return "detail";
    }

    /**
     * 获取秒杀商品的接口地址，以json数据接受
     * @param seckillId
     * @return
     */
    @RequestMapping(value = "{seckillId}/exposer",method = RequestMethod.GET,produces = "/application/json;charset=UTF-8")
    @ResponseBody//表示接受数据为json
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId){
        SeckillResult<Exposer> result;
        try{
            Exposer exposer = seckillGoodsServer.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true,exposer);
        }catch(Exception e){
           e.printStackTrace();
           result = new SeckillResult<Exposer>(false,e.getMessage());
        }
       return result;
    }

    /**
     * 获得秒杀接口后执行秒杀，根据秒杀状态返回不同的错误提示 以及正确的执行秒杀操作
     * @param seckillId
     * @param md5
     * @param userPhone
     * @return
     */
    @RequestMapping(value = "{seckillId}/{md5}/execution",method = RequestMethod.POST,produces = "/application/json;charset=UTF-8")
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,@PathVariable("md5") String md5,@CookieValue(value = "userPhone",required = false) Long userPhone){
       if(userPhone == null){
           return new SeckillResult<SeckillExecution>(false,"用户未注册");
       }
       try {
          SeckillExecution seckillExecution =  seckillGoodsServer.executeSeckillProcedure(seckillId,userPhone,md5);
           return new SeckillResult<SeckillExecution>(true,seckillExecution);
       }catch(RepeatKillException e1){
           SeckillExecution seckillExecution = new SeckillExecution(seckillId,SeckillStatuEnums.REPEAT_KILL);
           return new SeckillResult<SeckillExecution>(false,seckillExecution);
       }catch (SeckillCloseException e2){
           SeckillExecution seckillExecution = new SeckillExecution(seckillId,SeckillStatuEnums.END);
           return new SeckillResult<SeckillExecution>(false,seckillExecution);
       }catch(Exception e){
            SeckillExecution seckillExecution = new SeckillExecution(seckillId,SeckillStatuEnums.INNER_ERROR);
            return new SeckillResult<SeckillExecution>(false,seckillExecution);
       }
    }

    /**
     * 获取系统当前时间
     */
    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time(){
        Date now = new Date();
        return new SeckillResult<Long>(true,now.getTime());
    }
}
