package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class APIResponse<T> {
	
	
	int recordCount;
	public int getRecordCount() {
		return recordCount;
	}

	public T getResponse() {
		return response;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public void setResponse(T response) {
		this.response = response;
	}

	T response;
	
	public APIResponse(int recordCount, T response) {
		super();
		this.recordCount = recordCount;
		this.response = response;
	}
	
}
