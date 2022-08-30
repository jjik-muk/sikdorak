package com.jjikmuk.sikdorak.comment.controller.response;

import com.jjikmuk.sikdorak.common.controller.response.CursorPageResponse;
import java.util.List;

public record CommentSearchPagingResponse(
	List<CommentSearchResponse> comments,
	CursorPageResponse page
) {

}
