package org.ume.school.modules.project;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.entity.Project;
import org.ume.school.modules.project.money.ProjectMoneyService;

import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.modules.user.UserService;

/**
 * Created by Zz on 2018/8/26.
 */
@RestController
@RequestMapping("/v0.1/project")
public class ProjectController {

    @Resource
    private ProjectService projectService;
    
    @Resource
    private ProjectMoneyService projectMoneyService;

    @Resource
    private UserService userService;

    /**
     * 获取首页推荐项目
     */
    @RequestMapping(value = "/recommend", method = RequestMethod.GET)
    public Response<List<Project>> getRecommends() {
        List<Project> projects = projectService.findRecommend();
        return new Response<>(projects);
    }

    /**
     * 获取项目列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Response<List<Project>> getProjects() {
        List<Project> projects = projectService.findByPublished();
        return new Response<>(projects);
    }

    /**
     * 获取项目详情
     */
    @RequestMapping(value = "/{project_id}", method = RequestMethod.GET)
    public Response<Project> getProjectById(@PathVariable("project_id") String projectId) {
        Project project = projectService.findById(projectId);
        project = projectService.parse(project);
        project.setProjectMoneys(projectMoneyService.parse(project.getProjectMoneys()));
        return new Response<>(project);
    }
}
