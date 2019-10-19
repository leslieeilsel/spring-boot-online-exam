/***********************************************************
 * @Description : 考试服务
 * @author      : 梁山广(Laing Shan Guang)
 * @date        : 2019-05-28 08:04
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
package com.huawei.l00379880.exam.controller;

import com.huawei.l00379880.exam.entity.Exam;
import com.huawei.l00379880.exam.service.ExamService;
import com.huawei.l00379880.exam.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Api(tags = "Exam APIs")
@RequestMapping("/exam")
public class ExamController {
    @Autowired
    private ExamService examService;

    @GetMapping("/question/list")
    @ApiOperation("获取问题的列表")
    ResultVO<QuestionPageVo> getQuestionList(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) {
        ResultVO<QuestionPageVo> resultVO;
        try {
            QuestionPageVo questionPageVo = examService.getQuestionList(pageNo, pageSize);
            resultVO = new ResultVO<>(0, "获取问题列表成功", questionPageVo);
        } catch (Exception e) {
            e.printStackTrace();
            resultVO = new ResultVO<>(-1, "获取问题列表失败", null);
        }
        return resultVO;
    }

    @PostMapping("/question/update")
    @ApiOperation("更新问题")
    ResultVO<String> questionUpdate(@RequestBody QuestionVo questionVo) {
        // 完成问题的更新
        System.out.println(questionVo);
        try {
            examService.updateQuestion(questionVo);
            return new ResultVO<>(0, "更新问题成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultVO<>(-1, "更新问题失败", null);
        }
    }

    @PostMapping("/question/create")
    @ApiOperation("创建问题")
    ResultVO<String> questionCreate(@RequestBody QuestionCreateSimplifyVo questionCreateSimplifyVo, HttpServletRequest request) {
        QuestionCreateVo questionCreateVo = new QuestionCreateVo();
        // 把能拷贝过来的属性都拷贝过来
        BeanUtils.copyProperties(questionCreateSimplifyVo, questionCreateVo);
        // 设置创建者信息
        String userId = (String) request.getAttribute("user_id");
        questionCreateVo.setQuestionCreatorId(userId);
        System.out.println(questionCreateVo);
        try {
            examService.questionCreate(questionCreateVo);
            return new ResultVO<>(0, "问题创建成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultVO<>(-1, "创建问题失败", null);
        }
    }

    @GetMapping("/question/selection")
    @ApiOperation("获取问题分类的相关选项")
    ResultVO<QuestionSelectionVo> getSelections() {
        QuestionSelectionVo questionSelectionVo = examService.getSelections();
        if (questionSelectionVo != null) {
            return new ResultVO<>(0, "获取问题分类选项成功", questionSelectionVo);
        } else {
            return new ResultVO<>(-1, "获取问题分类选项失败", null);
        }
    }

    @GetMapping("/list")
    @ApiOperation("获取考试列表")
    ResultVO<ExamPageVo> getExamList(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) {
        // 需要拼接前端需要的考试列表对象
        ResultVO<ExamPageVo> resultVO;
        try {
            ExamPageVo examPageVo = examService.getExamList(pageNo, pageSize);
            resultVO = new ResultVO<>(0, "获取考试列表成功", examPageVo);
        } catch (Exception e) {
            e.printStackTrace();
            resultVO = new ResultVO<>(-1, "获取考试列表失败", null);
        }
        return resultVO;
    }

    @GetMapping("/question/type/list")
    @ApiOperation("获取问题列表，按照单选、多选和判断题分类返回")
    ResultVO<ExamQuestionTypeVo> getExamQuestionTypeList() {
        // 获取问题的分类列表
        ResultVO<ExamQuestionTypeVo> resultVO;
        try {
            ExamQuestionTypeVo examQuestionTypeVo = examService.getExamQuestionType();
            resultVO = new ResultVO<>(0, "获取问题列表成功", examQuestionTypeVo);
        } catch (Exception e) {
            e.printStackTrace();
            resultVO = new ResultVO<>(-1, "获取问题列表失败", null);
        }
        return resultVO;
    }

    @PostMapping("/create")
    @ApiOperation("创建考试")
    ResultVO<Exam> createExam(@RequestBody ExamCreateVo examCreateVo, HttpServletRequest request) {
        // 从前端传参数过来，在这里完成考试的入库
        ResultVO<Exam> resultVO;
        String userId = (String) request.getAttribute("user_id");
        try {
            Exam exam = examService.create(examCreateVo, userId);
            resultVO = new ResultVO<>(0, "创建考试成功", exam);
        } catch (Exception e) {
            e.printStackTrace();
            resultVO = new ResultVO<>(-1, "创建考试失败", null);
        }
        return resultVO;
    }

    @GetMapping("/card/list")
    @ApiOperation("获取考试列表，适配前端卡片列表")
    ResultVO<List<ExamCardVo>> getExamCardList() {
        // 获取考试列表卡片
        ResultVO<List<ExamCardVo>> resultVO;
        try {
            List<ExamCardVo> examCardVoList = examService.getExamCardList();
            resultVO = new ResultVO<>(0, "获取考试列表卡片成功", examCardVoList);
        } catch (Exception e) {
            e.printStackTrace();
            resultVO = new ResultVO<>(-1, "获取考试列表卡片失败", null);
        }
        return resultVO;
    }

    @GetMapping("/detail/{id}")
    @ApiOperation("根据考试的id，获取考试详情")
    ResultVO<ExamDetailVo> getExamDetail(@PathVariable Integer id){
        // Todo:根据id获取考试详情

        return null;
    }

}