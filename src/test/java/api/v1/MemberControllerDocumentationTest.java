package api.v1;

import api.v1.controller.UserController;
import api.v1.dto.PatchUserDto;
import api.v1.dto.PostUserDto;
import api.v1.dto.ResponseUserDto;
import api.v1.entity.User;
import api.v1.entity.bussinessType.Bussiness;
import api.v1.entity.region.CityEnum;
import api.v1.entity.region.RegionEnum;
import api.v1.mapper.UserMapper;
import api.v1.service.UserService;
import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class MemberControllerDocumentationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper mapper;

    @Autowired
    private Gson gson;

    @Test
    @DisplayName("postMapping 테스트")
    public void postUserTest() throws Exception {
        PostUserDto post = PostUserDto.builder()
                .name("홍길동")
                .email("hgd@gmail.com")
                .city(CityEnum.강남구)
                .region(RegionEnum.서울특별시)
                .bussiness(Bussiness.건설업)
                .build();

        String content = gson.toJson(post);

        ResponseUserDto responseBody = ResponseUserDto.builder()
                .userId(1L)
                .name("홍길동")
                .email("hgd@gmail.com")
                .city(CityEnum.강남구)
                .region(RegionEnum.서울특별시)
                .bussiness(Bussiness.건설업)
                .build();

        given(mapper.postDtoToUser(Mockito.any(PostUserDto.class))).willReturn(new User());
        given(userService.saveUser(Mockito.any(User.class))).willReturn(new User());
        given(mapper.userToResponseDto(Mockito.any(User.class))).willReturn(responseBody);

        ResultActions actions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/user")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content));
        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value(post.getName()))
                .andExpect(jsonPath("$.data.email").value(post.getEmail()))
                .andExpect(jsonPath("$.data.city").value(post.getCity().getValue()))
                .andExpect(jsonPath("$.data.region").value(post.getRegion().getValue()))
                .andExpect(jsonPath("$.data.bussiness").value(post.getBussiness().getValue()))
                .andDo(document("post-user",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                List.of(fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("email"),
                                        fieldWithPath("city").type(JsonFieldType.STRING).description("주소-시/도"),
                                        fieldWithPath("region").type(JsonFieldType.STRING).description("주소-지역"),
                                        fieldWithPath("bussiness").type(JsonFieldType.STRING).description("업종")
                                )
                        ),
                        responseFields(
                                Arrays.asList(
                                    fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터").optional(),
                                        fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("회원 이메일"),
                                        fieldWithPath("data.city").type(JsonFieldType.STRING).description("회원 주소지(시)"),
                                        fieldWithPath("data.region").type(JsonFieldType.STRING).description("회원 주소지(지역)"),
                                        fieldWithPath("data.bussiness").type(JsonFieldType.STRING).description("회원 업종")
                                )
                        )));
    }

    @Test
    @DisplayName("patchMapping 테스트")
    public void patchUserTest() throws Exception {
        Long userId = 1L;
        PatchUserDto patch = PatchUserDto.builder()
                .name("홍길동")
                .email("hgd@gmail.com")
                .city(CityEnum.강남구)
                .region(RegionEnum.서울특별시)
                .bussiness(Bussiness.농업)
                .build();
        patch.setUserId(userId);
        String content = gson.toJson(patch);

        ResponseUserDto responseDto = ResponseUserDto.builder()
                .userId(1L)
                .name("홍길동")
                .email("hgd@gmail.com")
                .city(CityEnum.강남구)
                .region(RegionEnum.서울특별시)
                .bussiness(Bussiness.농업)
                .build();

        given(mapper.patchDtoToUser(Mockito.any(PatchUserDto.class))).willReturn(new User());
        given(userService.updateUser(Mockito.any(User.class))).willReturn(new User());
        given(mapper.userToResponseDto(Mockito.any(User.class))).willReturn(responseDto);

        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders
                        .patch("/user/{user-id}",userId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userId").value(patch.getUserId()))
                .andExpect(jsonPath("$.data.name").value(patch.getName()))
                .andExpect(jsonPath("$.data.email").value(patch.getEmail()))
                .andExpect(jsonPath("$.data.city").value(patch.getCity().getValue()))
                .andExpect(jsonPath("$.data.region").value(patch.getRegion().getValue()))
                .andExpect(jsonPath("$.data.bussiness").value(patch.getBussiness().name()))
                .andDo(document("patch-user",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                Arrays.asList(parameterWithName("user-id").description("회원 식별자 ID"))
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("userId").type(JsonFieldType.NUMBER).description("회원 식별자").ignored(),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름").optional(),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("email").optional(),
                                        fieldWithPath("city").type(JsonFieldType.STRING).description("시/군").optional(),
                                        fieldWithPath("region").type(JsonFieldType.STRING).description("지역").optional(),
                                        fieldWithPath("bussiness").type(JsonFieldType.STRING).description("업종").optional()
                                )
                        ),
                        responseFields(
                                Arrays.asList(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터").optional(),
                                        fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("data.city").type(JsonFieldType.STRING).description("주소지"),
                                        fieldWithPath("data.region").type(JsonFieldType.STRING).description("시/도"),
                                        fieldWithPath("data.bussiness").type(JsonFieldType.STRING).description("업종")
                                )
                        )
                ));

    }

    @Test
    @DisplayName("파라미터가 user-id인 getMapping 테스트")
    void getUserTestParameterId() throws Exception {
        Long userId = 1L;
        ResponseUserDto response = ResponseUserDto.builder()
                .userId(userId)
                .name("하현우")
                .email("abc@gmail.com")
                .city(CityEnum.안산시)
                .region(RegionEnum.경기도)
                .bussiness(Bussiness.방송통신)
                .build();

        given(userService.findUserById(Mockito.anyLong())).willReturn(new User());
        given(mapper.userToResponseDto(Mockito.any(User.class))).willReturn(response);

        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/user/{user-id}",userId)
                        .accept(MediaType.APPLICATION_JSON));

        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userId").value(userId))
                .andExpect(jsonPath("$.data.name").value(response.getName()))
                .andExpect(jsonPath("$.data.email").value(response.getEmail()))
                .andExpect(jsonPath("$.data.city").value(response.getCity().getValue()))
                .andExpect(jsonPath("$.data.region").value(response.getRegion().getValue()))
                .andExpect(jsonPath("$.data.bussiness").value(response.getBussiness().name()))
                .andDo(
                        document("get-user",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        Arrays.asList(parameterWithName("user-id").description("회원 식별자 ID"))
                                ),
                                responseFields(
                                        Arrays.asList(
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터").optional(),
                                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름"),
                                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
                                                fieldWithPath("data.city").type(JsonFieldType.STRING).description("시/군"),
                                                fieldWithPath("data.region").type(JsonFieldType.STRING).description("지역"),
                                                fieldWithPath("data.bussiness").type(JsonFieldType.STRING).description("업종")
                                        )
                                )
                        ));
    }

    @Test
    @DisplayName("getmapping search")
    void getTest() throws Exception {
        String page = "1", size = "10";

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", page);
        queryParams.add("size", size);


        User user1 = User.builder().userId(1L)
                .name("하현우")
                .email("abc@gmail.com")
                .city(CityEnum.안산시)
                .region(RegionEnum.경기도)
                .bussiness(Bussiness.방송통신)
                .build();
        User user2 = User.builder().userId(1L)
                .name("김연아")
                .email("abc1@gmail.com")
                .city(CityEnum.강남구)
                .region(RegionEnum.서울특별시)
                .bussiness(Bussiness.금융)
                .build();

        ResponseUserDto response1 = ResponseUserDto.builder().userId(1L)
                .name("하현우")
                .email("abc@gmail.com")
                .city(CityEnum.안산시)
                .region(RegionEnum.경기도)
                .bussiness(Bussiness.방송통신)
                .build();

        ResponseUserDto response2 = ResponseUserDto.builder().userId(1L)
                .name("김연아")
                .email("abc1@gmail.com")
                .city(CityEnum.강남구)
                .region(RegionEnum.서울특별시)
                .bussiness(Bussiness.금융)
                .build();


        Page<User> users = new PageImpl<>(List.of(user1, user2),
                PageRequest.of(0,10, Sort.Direction.DESC,"name"),2);

        List<ResponseUserDto> responses = List.of(response1, response2);

        given(userService.findAll(Mockito.anyInt(),Mockito.anyInt())).willReturn(users);
        given(mapper.userToResponseDtos(Mockito.anyList())).willReturn(responses);

        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/user")
                        .params(queryParams)
                        .accept(MediaType.APPLICATION_JSON));

        MvcResult result =
                actions
                        .andExpect(status().isOk())
                        .andDo(document(
                                "get-users",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestParameters(
                                        List.of(
                                                parameterWithName("page").description("page 번호"),
                                                parameterWithName("size").description("page size")
                                        )
                                ),
                                responseFields(
                                        Arrays.asList(
                                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터").optional(),
                                                fieldWithPath("data[].userId").type(JsonFieldType.NUMBER).description("회원 아이디"),
                                                fieldWithPath("data[].name").type(JsonFieldType.STRING).description("회원이름"),
                                                fieldWithPath("data[].email").type(JsonFieldType.STRING).description("회원 이메일"),
                                                fieldWithPath("data[].city").type(JsonFieldType.STRING).description("시/군/구"),
                                                fieldWithPath("data[].region").type(JsonFieldType.STRING).description("지역(도)"),
                                                fieldWithPath("data[].bussiness").type(JsonFieldType.STRING).description("업종"),
                                                fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이지 정보").optional(),
                                                fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("페이지 번호").optional(),
                                                fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지 사이즈").optional(),
                                                fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 건 수").optional(),
                                                fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수").optional()

                                        )
                                )
                        )).andReturn();
        List list = JsonPath.parse(result.getResponse().getContentAsString()).read("$.data");
        assertThat(list.size()).isEqualTo(2);
    }
    @Test
    void searchTest() throws Exception {



        User user1 = User.builder().userId(1L)
                .name("하현우")
                .email("abc@gmail.com")
                .city(CityEnum.강남구)
                .region(RegionEnum.서울특별시)
                .bussiness(Bussiness.방송통신)
                .build();
        User user2 = User.builder().userId(1L)
                .name("김연아")
                .email("abc1@gmail.com")
                .city(CityEnum.강남구)
                .region(RegionEnum.서울특별시)
                .bussiness(Bussiness.금융)
                .build();

        ResponseUserDto response1 = ResponseUserDto.builder().userId(1L)
                .name("하현우")
                .email("abc@gmail.com")
                .city(CityEnum.안산시)
                .region(RegionEnum.경기도)
                .bussiness(Bussiness.방송통신)
                .build();

        ResponseUserDto response2 = ResponseUserDto.builder().userId(1L)
                .name("김연아")
                .email("abc1@gmail.com")
                .city(CityEnum.강남구)
                .region(RegionEnum.서울특별시)
                .bussiness(Bussiness.금융)
                .build();

        Page<User> users = new PageImpl<>(List.of(user1, user2),
                PageRequest.of(0,10, Sort.Direction.DESC,"name"),2);

        List<ResponseUserDto> responses = List.of(response1, response2);
        given(userService.findAllByUserCustomRepositoryImpl(Mockito.anyInt(),Mockito.anyInt(),Mockito.any(CityEnum.class),Mockito.any(Bussiness.class),Mockito.anyString())).willReturn(users);
        given(mapper.userToResponseDtos(Mockito.anyList())).willReturn(responses);



        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/user/search")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("page","1")
                        .param("size","10")
                        .param("city","강남구")
                        .param("bussiness","방송통신")
                        .param("name","하현우")

        );

        MvcResult result = actions.andExpect(status().isOk())
                .andDo(document(
                        "search-users",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                List.of(
                                        parameterWithName("page").description("page 번호").optional(),
                                        parameterWithName("size").description("page size").optional(),
                                        parameterWithName("city").description("시/군").optional(),
                                        parameterWithName("bussiness").description("업종").optional(),
                                        parameterWithName("name").description("이름").optional()
                                )
                        ),
                        responseFields(
                                Arrays.asList(
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터").optional(),
                                        fieldWithPath("data[].userId").type(JsonFieldType.NUMBER).description("회원 아이디"),
                                        fieldWithPath("data[].name").type(JsonFieldType.STRING).description("회원이름"),
                                        fieldWithPath("data[].email").type(JsonFieldType.STRING).description("회원 이메일"),
                                        fieldWithPath("data[].city").type(JsonFieldType.STRING).description("시/군/구"),
                                        fieldWithPath("data[].region").type(JsonFieldType.STRING).description("지역(도)"),
                                        fieldWithPath("data[].bussiness").type(JsonFieldType.STRING).description("업종"),
                                        fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이지 정보").optional(),
                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("페이지 번호").optional(),
                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지 사이즈").optional(),
                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 건 수").optional(),
                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수").optional()

                                )
                        )
                )).andReturn();
        List list = JsonPath.parse(result.getResponse().getContentAsString()).read("$.data");
        assertThat(list.size()).isEqualTo(2);
    }





    @Test
    @DisplayName("deleteMapping test")
    void deleteTest() throws Exception {
        Long userId = 1L;
        willDoNothing().given(userService).deleteUser(Mockito.anyLong());

        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders.delete("/user/{user-id}",userId));

        actions.andExpect(status().isNoContent())
                .andDo(document("delete-user",
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("user-id").description("회원 식별자")
                        )
                ));


    }


}
