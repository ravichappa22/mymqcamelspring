package com.mymq.sample.configuration;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

public class CalimRequest implements Serializable {
	private String claimId;
	private Instant servieDateFrom;
	private Instant serviceDateTo;
	public String getClaimId() {
		return claimId;
	}
	public void setClaimId(String claimId) {
		this.claimId = claimId;
	}
	public Instant getServieDateFrom() {
		return servieDateFrom;
	}
	public void setServieDateFrom(Instant servieDateFrom) {
		this.servieDateFrom = servieDateFrom;
	}
	public Instant getServiceDateTo() {
		return serviceDateTo;
	}
	public void setServiceDateTo(Instant serviceDateTo) {
		this.serviceDateTo = serviceDateTo;
	}
	public CalimRequest(String claimId, Instant servieDateFrom, Instant serviceDateTo) {
		super();
		this.claimId = claimId;
		this.servieDateFrom = servieDateFrom;
		this.serviceDateTo = serviceDateTo;
	}
	@Override
	public String toString() {
		return "CalimRequest [claimId=" + claimId + ", servieDateFrom=" + servieDateFrom + ", serviceDateTo="
				+ serviceDateTo + "]";
	}
	
	
	
}
