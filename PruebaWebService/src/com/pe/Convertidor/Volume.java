package com.pe.Convertidor;


public class Volume {
	
	

	public Support getSupport() {
		return support;
	}

	public void setSupport(Support support) {
		this.support = support;
	}

	private String status;
	private Boolean managed;
	private String name;
    private Support support;
	private String storage_pool;
	private String id;
	private int size;
	//private List<String> mapped_wwpns;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getManaged() {
		return managed;
	}

	public void setManaged(Boolean managed) {
		this.managed = managed;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStorage_pool() {
		return storage_pool;
	}

	public void setStorage_pool(String storage_pool) {
		this.storage_pool = storage_pool;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
   
	@Override
	public String toString() {
		return "Volume [status=" + status + ", managed=" + managed + ", name="
				+ name + ", storage_pool=" + storage_pool + ", id=" + id
				+ ", size=" + size + "]";
	}

}
