package com.aegis.image_shop.contorller;

import com.aegis.image_shop.common.security.domain.CustomUser;
import com.aegis.image_shop.domain.Board;
import com.aegis.image_shop.domain.Member;
import com.aegis.image_shop.dto.CodeLabelValue;
import com.aegis.image_shop.dto.PaginationDTO;
import com.aegis.image_shop.service.BoardService;
import com.aegis.image_shop.vo.PageRequestVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService service;

    @GetMapping("/register")
    @PreAuthorize("hasRole('MEMBER')")
    public void registerForm(Model model, Authentication authentication) throws Exception{
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        Member member = customUser.getMember();

        Board board = new Board();

        board.setWriter(member.getUserId());

        model.addAttribute(board);
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('MEMBER')")
    public String register(@Validated Board board, BindingResult result, RedirectAttributes rttr) throws Exception{
        if(result.hasErrors()){
            return "board/register";
        }

        service.register(board);

        rttr.addFlashAttribute("msg", "SUCCESS");

        return "redirect:/board/list";
    }

    @GetMapping("/list")
    public void list(@ModelAttribute("pgrq") PageRequestVO pageRequestVO, Model model) throws Exception{
        Page<Board> page = service.list(pageRequestVO);
        model.addAttribute("pgntn", new PaginationDTO<>(page));

        List<CodeLabelValue> searchTypeCodeValueList = new ArrayList<CodeLabelValue>();
        searchTypeCodeValueList.add(new CodeLabelValue("n","---"));
        searchTypeCodeValueList.add(new CodeLabelValue("t","Title"));
        searchTypeCodeValueList.add(new CodeLabelValue("c","Content"));
        searchTypeCodeValueList.add(new CodeLabelValue("w","Writer"));
        searchTypeCodeValueList.add(new CodeLabelValue("tc","Title OR Content"));
        searchTypeCodeValueList.add(new CodeLabelValue("cw","Content OR Writer"));
        searchTypeCodeValueList.add(new CodeLabelValue("tcw","Title OR Content OR Writer"));

        model.addAttribute("searchTypeCodeValueList", searchTypeCodeValueList);
    }

    @GetMapping("/read")
    public void read(Long boardNo, @ModelAttribute("pgrq") PageRequestVO pageRequestVO, Model model) throws Exception{
        model.addAttribute(service.read(boardNo));
    }

    @GetMapping("/modigy")
    @PreAuthorize("hasRole('MEMBER')")
    public void modifyForm(Long boardNo, @ModelAttribute("pgrq") PageRequestVO pageRequestVO, Model model) throws Exception{
        model.addAttribute(service.read(boardNo));
    }

    @PostMapping("/modify")
    @PreAuthorize("hasRole('MEMBER') and principal.username == #board.writer")
    public String modify(@Validated Board board, BindingResult result, @ModelAttribute("pgrq") PageRequestVO pageRequestVO, RedirectAttributes rttr) throws Exception{
        if(result.hasErrors()){
            return "board/modify";
        }

        service.modify(board);

        rttr.addAttribute("page", pageRequestVO.getPage());
        rttr.addAttribute("sizePerPage", pageRequestVO.getSizePerPage());
        rttr.addAttribute("searchType", pageRequestVO.getSearchType());
        rttr.addAttribute("keyword", pageRequestVO.getKeyword());

        rttr.addFlashAttribute("msg", "SUCCESS");

        return "redirect:/board/list";
    }

    @PostMapping("/remove")
    @PreAuthorize("(hasRole('MEMBER') and principal.username == #writer) or hasRole('ADMIN')")
    public String remove(Long boardNo, PageRequestVO pageRequestVO, RedirectAttributes rttr, String writer) throws Exception{
        service.remove(boardNo);

        rttr.addAttribute("page", pageRequestVO.getPage());
        rttr.addAttribute("sizePerPage", pageRequestVO.getSizePerPage());
        rttr.addAttribute("searchType", pageRequestVO.getSearchType());
        rttr.addAttribute("keyword", pageRequestVO.getKeyword());

        rttr.addFlashAttribute("msg", "SUCCESS");

        return "redirect:/board/list";
    }
}
